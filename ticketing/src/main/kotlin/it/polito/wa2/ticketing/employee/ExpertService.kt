package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import java.util.*

interface ExpertService {
    fun getTickets(idExpert: UUID): List<TicketDTO?>
    fun reassignTicket(ticketId: Long, expertId: UUID)
    fun addMessage(idTicket: Long, message: MessageDTO, expertId: UUID)
}