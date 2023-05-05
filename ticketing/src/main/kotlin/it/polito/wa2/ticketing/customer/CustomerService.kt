package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.TicketWithMessagesDTO

interface CustomerService {

    fun getCustomer(id: Long): CustomerDTO?

    fun getCustomerTickets(id: Long): Set<TicketDTO>?

    fun getByCustomerTicketMessages(idCustomer: Long, idTicket: Long): Set<MessageDTO>?
}