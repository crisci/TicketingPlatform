package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.ticket.TicketDTO

interface CustomerService {

    fun getCustomer(id: Long): CustomerDTO?

    fun getCustomerTickets(id: Long): Set<TicketDTO>?
}