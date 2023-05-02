package it.polito.wa2.ticketing.Ticket

import it.polito.wa2.ticketing.Cusotmer.Customer
import it.polito.wa2.ticketing.Utils.PriorityLevel

data class TicketDTO(
    var title: String,
    var description: String,
    var priority: PriorityLevel,
    var customer: Customer?
)

fun Ticket.toDTO(): TicketDTO {
    return TicketDTO(title,description,priority,customer)
}
