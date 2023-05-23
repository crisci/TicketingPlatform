package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.history.HistoryDTO
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus

interface TicketService {
    fun getTicketsWithMessagesByCustomerId(customerId: Long): Set<TicketWithMessagesDTO>?
    fun getTicketsByCustomerId(customerId: Long): Set<TicketDTO>?
    fun reassignTicket(ticketId: Long, idExpert: Long)
    fun closeTicket(ticketId: Long, idExpert: Long)
    fun getMessages(ticketId: Long, idExpert: Long): List<MessageDTO>
    fun getStatus(ticketId: Long): TicketStatus
    fun addMessage(idTicket: Long, message: MessageDTO)
    fun addTicket(ticket: TicketDTO, idCustomer: Long)
    fun resolveTicket(ticketId: Long)
    fun reopenTicket(ticketId: Long)
    fun assignTicket(idTicket: Long, idExpert: Long, priorityLevel: PriorityLevel)
    fun getTicketsByStatus(status: TicketStatus?): Set<TicketDTO?>


}