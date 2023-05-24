package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.history.HistoryDTO
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import java.util.UUID

interface TicketService {
    fun getTicketsWithMessagesByCustomerId(customerId: Long): Set<TicketWithMessagesDTO>?
    fun getTicketsByCustomerId(customerId: Long): Set<TicketDTO>?
    fun reassignTicket(ticketId: Long, idExpert: UUID)
    fun closeTicket(ticketId: Long, idExpert: UUID)
    fun getMessages(ticketId: Long, idExpert: UUID): List<MessageDTO>
    fun getStatus(ticketId: Long): TicketStatus
    fun addMessage(idTicket: Long, message: MessageDTO)
    fun addTicket(ticket: TicketDTO, idCustomer: Long)
    fun resolveTicket(ticketId: Long)
    fun reopenTicket(ticketId: Long)
    fun assignTicket(idTicket: Long, idExpert: UUID, priorityLevel: PriorityLevel)
    fun getTicketsByStatus(status: TicketStatus?): Set<TicketDTO?>


}