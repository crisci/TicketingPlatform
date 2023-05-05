package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeRepository
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service @Transactional
class TicketServiceImpl(private val ticketRepository: TicketRepository,private val employeeRepository: EmployeeRepository): TicketService {
    override fun getTicketsWithMessagesByCustomerId(customerId: Long): Set<TicketWithMessagesDTO>? {
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketWithMessagesDTO() }.toSet()
    }

    override fun getTicketsByCustomerId(customerId: Long): Set<TicketDTO>? {
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketDTO() }.toSet()
    }

    override fun reassignTicket(ticketId: Long, idExpert: Long) {
        ticketRepository.findById(ticketId).ifPresentOrElse(
            {
                var admin: Employee? = null
                for (h: History in it.history.sorted()) {
                    if (h.employee == null || h.employee?.type == EmployeeRole.ADMIN) {
                        admin = h.employee
                        break
                    }
                }
                it.addHistory(History().create(TicketStatus.OPEN, LocalDateTime.now(), it, admin))
            },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
    }

    override fun closeTicket(ticketId: Long, idExpert: Long) {
        val expert = employeeRepository.findByIdOrNull(idExpert)!!
        ticketRepository.findById(ticketId).ifPresentOrElse(
            { it.addHistory(History().create(TicketStatus.CLOSED, LocalDateTime.now(), it, expert)) },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
    }

    override fun getMessages(ticketId: Long, idExpert: Long): List<MessageDTO> {
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        return employeeRepository.findByIdOrNull(idExpert)!!.listOfMessages.stream()
            .filter { it.ticket == ticket }.sorted().map {
                it.toDTO()
            }.toList()
    }

    override fun getStatus(ticketId: Long, idExpert: Long): TicketStatus {
        return ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
            .history.stream().sorted().findFirst().get().state
    }
}