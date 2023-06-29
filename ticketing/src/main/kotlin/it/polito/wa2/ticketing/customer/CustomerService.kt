package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.product.ProductDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import java.util.UUID

interface CustomerService {

    fun getTicketsWithMessagesByCustomerId(idCustomer: UUID, idTicket: Long): List<MessageDTO>?
    fun getCustomerByEmail(email: String) : CustomerDTO?
    fun getCustomers() : List<CustomerDTO>
    fun getTicketsByCustomerId(customerId: UUID): List<TicketDTO>?
    fun addMessage(idTicket: Long, message: MessageDTO)
    fun addTicket(ticket: TicketDTO, idCustomer: UUID)
    fun resolveTicket(ticketId: Long)
    fun reopenTicket(ticketId: Long)
    fun closeTicket(ticketId: Long)
    fun productRegistration(customerId: UUID, ean: String)
    fun getRegisteredProducts(customerId: UUID): List<ProductDTO>?
    fun deleteProduct(customerId: UUID, ean: String)

}

