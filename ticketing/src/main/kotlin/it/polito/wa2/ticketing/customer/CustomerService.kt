package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO

interface CustomerService {

    fun getCustomer(id: Long): CustomerDTO?

    fun getCustomerTickets(id: Long): Set<TicketDTO>?

    fun getByCustomerTicketMessages(idCustomer: Long, idTicket: Long): Set<MessageDTO>?

    fun getCustomerByEmail(email: String) : CustomerDTO?

    fun getCustomers() : List<CustomerDTO>

    fun insertCustomer(customerWithPasswordDTO: CustomerWithPasswordDTO)

    fun updateCustomer(customerWithPasswordDTO: CustomerWithPasswordDTO, email: String)

    fun resolveTicket(ticketId: Long)

    fun reopenTicket(ticketId: Long)
}