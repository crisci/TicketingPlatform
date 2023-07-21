package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.customer.CustomerNotFoundException
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeDTO
import it.polito.wa2.ticketing.employee.toEmployeeDTO
import it.polito.wa2.ticketing.employee.EmployeeRepository
import it.polito.wa2.ticketing.history.*
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.MessageRepository
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.history.toDTO
import it.polito.wa2.ticketing.product.ProductRepository
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service @Transactional
class TicketServiceImpl(private val ticketRepository: TicketRepository,
                        private val employeeRepository: EmployeeRepository,
                        private val historyRepository: HistoryRepository,
                        private val customerRepository: CustomerRepository,
                        private val productRepository: ProductRepository,
                        private val messageRepository: MessageRepository,
): TicketService {
    override fun getTicketsWithMessagesByCustomerId(customerId: UUID): Set<TicketWithMessagesDTO> {
        if(!customerRepository.existsById(customerId))
            throw CustomerNotFoundException("No customer found with specified id!")
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketWithMessagesDTO() }.toSet()
    }

    @Secured("ROLE_Manager", "ROLE_Client")
    override fun closeTicket(ticketId: Long) {
        val expert = historyRepository.findByTicketIdOrderByDateDesc(ticketId).first().employee
        ticketRepository.findById(ticketId).ifPresentOrElse(
            { it.addHistory(History().create(TicketStatus.CLOSED, LocalDateTime.now(), it, expert)); ticketRepository.save(it) },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
        ticketRepository.flush()
    }

    @Secured("ROLE_Manager", "ROLE_Client", "ROLE_Expert")
    override fun getMessages(ticketId: Long): List<MessageDTO> {
        return messageRepository.findMessagesByTicketId(ticketId).stream()
            .sorted().map {
                it.toDTO()
            }.toList()
    }

    @Secured("ROLE_Manager", "ROLE_Client", "ROLE_Expert")
    override fun getHistory(ticketId: Long):List<HistoryDTO>{
        return historyRepository.findByTicketIdOrderByDateDesc(ticketId).map{it.toDTO()}
    }

    @Secured("ROLE_Manager", "ROLE_Client","ROLE_Expert")
    override fun getStatus(ticketId: Long): TicketStatus {
        return ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
            .history.stream().sorted().findFirst().get().state
    }

    @Secured("ROLE_Manager", "ROLE_Client","ROLE_Expert")
    override fun getExpert(ticketId: Long): EmployeeDTO?{
        return historyRepository.findByTicketIdOrderByDateDesc(ticketId).first()?.employee?.toEmployeeDTO()
    }
}