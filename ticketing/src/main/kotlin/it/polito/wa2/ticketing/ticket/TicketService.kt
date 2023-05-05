package it.polito.wa2.ticketing.ticket

interface TicketService {

    fun getTicketsByCustomerId(customerId: Long): Set<TicketDTO>?
}