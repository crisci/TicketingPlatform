package it.polito.wa2.ticketing.ticket

interface TicketService {

    fun getTicketsWithMessagesByCustomerId(customerId: Long): Set<TicketWithMessagesDTO>?

    fun getTicketsByCustomerId(customerId: Long): Set<TicketDTO>?
}