package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.employee.EmployeeDTO
import it.polito.wa2.ticketing.history.HistoryDTO
import it.polito.wa2.ticketing.utils.TicketStatus
import java.util.UUID

interface TicketService {
    fun getTicketsWithMessagesByCustomerId(customerId: UUID): Set<TicketWithMessagesDTO>?
    fun closeTicket(ticketId: Long)
    fun getMessages(ticketId: Long): List<MessageDTO>
    fun getHistory(ticketId: Long):List<HistoryDTO>
    fun getStatus(ticketId: Long): TicketStatus
    fun getExpert(ticketId: Long): EmployeeDTO?
}