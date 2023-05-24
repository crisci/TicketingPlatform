package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.customer.CustomerDTO
import it.polito.wa2.ticketing.customer.CustomerNotFoundException
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.customer.toDTO
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.history.OperationNotPermittedException
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import it.polito.wa2.ticketing.ticket.toTicketDTO
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service @Transactional
class ManagerServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val ticketRepository: TicketRepository,
    private val historyRepository: HistoryRepository,
    private val customerRepository: CustomerRepository): ManagerService {
    override fun getTicketsByStatus(status: TicketStatus?): List<TicketDTO?> {
        return if(status != null) {
            historyRepository.findMostRecentStateByStatus(status).map { it.ticket?.toTicketDTO() }
        } else {
            historyRepository.findMostRecentState().map { it.ticket?.toTicketDTO() }
        }
    }

    override fun getCustomer(id: UUID): CustomerDTO? {
        return customerRepository.findById(id).orElseThrow {
            CustomerNotFoundException("Customer not found with the following id '${id}'")
        }
            .toDTO()
    }

    override fun getExpert(id: UUID): EmployeeDTO? {
        return employeeRepository.findById(id).orElseThrow {
            CustomerNotFoundException("Customer not found with the following id '${id}'")
        }
            .toEmployeeDTO()
    }

    override fun getExperts(): List<EmployeeDTO?>{
        return employeeRepository.findAll().map { it.toEmployeeDTO() }
    }


    override fun assignTicket(idTicket: Long, idExpert: UUID, priorityLevel: PriorityLevel) {
        val ticket = ticketRepository.findById(idTicket)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        val expert = employeeRepository.findByIdAndType(idExpert, EmployeeRole.EXPERT)
            .orElseThrow { ExpertNotFoundException("The specified expert has not been found!") }
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(idTicket)
        if(lastTicketHistory.first().state == TicketStatus.OPEN || lastTicketHistory.first().state == TicketStatus.REOPENED) {
            ticket.addHistory(
                History().create(TicketStatus.IN_PROGRESS, LocalDateTime.now(), ticket, expert)
            )
            ticket.priority = priorityLevel
            ticketRepository.save(ticket)
        } else {
            throw OperationNotPermittedException("This operation is not permitted for the current ticket's state!")
        }

    }

    override fun getTicketMessages(idTicket: Long): List<MessageDTO>? {
        return ticketRepository.findById(idTicket)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
            .listOfMessage.map { it.toDTO() }
    }


}