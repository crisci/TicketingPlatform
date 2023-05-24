package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.customer.CustomerDTO
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import java.util.*

interface ManagerService {

    fun getTicketsByStatus(status: TicketStatus?): List<TicketDTO?>

    fun assignTicket(idTicket: Long, idExpert: UUID, priorityLevel: PriorityLevel)

    fun getTicketMessages(idTicket: Long): List<MessageDTO>?

    fun getCustomer(id: UUID): CustomerDTO?

    fun getExpert(id: UUID): EmployeeDTO?

    fun getExperts(): List<EmployeeDTO?>

}