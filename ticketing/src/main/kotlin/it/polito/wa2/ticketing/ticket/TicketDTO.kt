package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.customer.Customer
import it.polito.wa2.ticketing.product.Product
import it.polito.wa2.ticketing.utils.PriorityLevel

data class TicketDTO(
    var title: String,
    var description: String,
    var priority: PriorityLevel,
    var customer: Customer?,
    var product: Product?
)

fun Ticket.toDTO(): TicketDTO {
    return TicketDTO(title,description,priority,customer, product)
}
