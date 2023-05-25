package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.customer.CustomerNotFoundException
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeRepository
import it.polito.wa2.ticketing.employee.ExpertNotFoundException
import it.polito.wa2.ticketing.history.*
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.MessageRepository
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.product.ProductNotFoundException
import it.polito.wa2.ticketing.product.ProductRepository
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service @Transactional
class TicketServiceImpl(private val ticketRepository: TicketRepository,
                        private val employeeRepository: EmployeeRepository,
                        private val historyRepository: HistoryRepository,
                        private val customerRepository: CustomerRepository,
                        private val productRepository: ProductRepository,
                        private val messageRepository: MessageRepository
): TicketService {
    override fun getTicketsWithMessagesByCustomerId(customerId: UUID): Set<TicketWithMessagesDTO> {
        if(!customerRepository.existsById(customerId))
            throw CustomerNotFoundException("No customer found with specified id!")
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketWithMessagesDTO() }.toSet()
    }

    override fun closeTicket(ticketId: Long) {
        val expert = historyRepository.findByTicketIdOrderByDateDesc(ticketId).first().employee
        ticketRepository.findById(ticketId).ifPresentOrElse(
            { it.addHistory(History().create(TicketStatus.CLOSED, LocalDateTime.now(), it, expert)); ticketRepository.save(it) },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
        ticketRepository.flush()
    }

    override fun getMessages(ticketId: Long): List<MessageDTO> {
        return messageRepository.findMessagesByTicketId(ticketId).stream()
            .sorted().map {
                it.toDTO()
            }.toList()
    }

    override fun getStatus(ticketId: Long): TicketStatus {
        return ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
            .history.stream().sorted().findFirst().get().state
    }



    override fun resolveTicket(ticketId: Long) {
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(ticketId)
        if(lastTicketHistory.isNotEmpty()) {
            val expert = lastTicketHistory.first().employee
            if (lastTicketHistory.first().state != TicketStatus.CLOSED && lastTicketHistory.first().state != TicketStatus.RESOLVED) {
                    ticket.addHistory(
                        History().create(
                            TicketStatus.RESOLVED,
                            LocalDateTime.now(),
                            ticket,
                            expert //If null than the ticket is closed before than an expert is assigned
                        )
                    )
            } else {
                throw OperationNotPermittedException("This operation is not permitted because the ticket is closed!")
            }
        } else {
                throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")
        }
    }

    override fun reopenTicket(ticketId: Long) {
        //Check the last history state related to the ticketId
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(ticketId)
        if(lastTicketHistory.isNotEmpty()) {
        if (lastTicketHistory.first().state == TicketStatus.CLOSED || lastTicketHistory.first().state == TicketStatus.RESOLVED) {
            ticket.addHistory(
                History().create(
                    TicketStatus.OPEN,
                    LocalDateTime.now(),
                    ticket,
                    null
                )
            )
        } else {
            throw OperationNotPermittedException("This operation is not permitted for the current ticket's state!")
        }} else {
            throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")
        }

    }


}