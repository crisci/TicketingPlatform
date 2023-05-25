package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.customer.CustomerNotFoundException
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeRepository
import it.polito.wa2.ticketing.history.*
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.utils.EmployeeRole
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
                        private val customerRepository: CustomerRepository
): TicketService {
    override fun getTicketsWithMessagesByCustomerId(customerId: UUID): Set<TicketWithMessagesDTO> {
        if(!customerRepository.existsById(customerId))
            throw CustomerNotFoundException("No customer found with specified id!")
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketWithMessagesDTO() }.toSet()
    }

    override fun reassignTicket(ticketId: Long, idExpert: UUID) {
        ticketRepository.findById(ticketId).ifPresentOrElse(
            {
                if (historyRepository.findByTicketIdOrderByDateDesc(ticketId).first().state != TicketStatus.OPEN) {

                    var admin: Employee? = null
                    for (h: History in it.history.sorted()) {
                        if (h.employee == null || h.employee?.type == EmployeeRole.MANAGER) {
                            admin = h.employee
                            break
                        }
                    }
                    it.addHistory(History().create(TicketStatus.OPEN, LocalDateTime.now(), it, admin))
                    ticketRepository.save(it)
                } else {
                    throw OperationNotPermittedException("The ticket is still open!")
                }
            },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
        ticketRepository.flush()
    }

    override fun closeTicket(ticketId: Long, idExpert: UUID) {
        val expert = employeeRepository.findByIdOrNull(idExpert)!!
        ticketRepository.findById(ticketId).ifPresentOrElse(
            { it.addHistory(History().create(TicketStatus.CLOSED, LocalDateTime.now(), it, expert)); ticketRepository.save(it) },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
        ticketRepository.flush()
    }

    override fun getMessages(ticketId: Long, idExpert: UUID): List<MessageDTO> {
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        return employeeRepository.findByIdOrNull(idExpert)!!.listOfMessages.stream()
            .filter { it.ticket == ticket }.sorted().map {
                it.toDTO()
            }.toList()
    }

    override fun getStatus(ticketId: Long): TicketStatus {
        return ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
            .history.stream().sorted().findFirst().get().state
    }

}