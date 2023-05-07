package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.toTicketDTO

data class CustomerWithTicketsDTO (
    val customerDTO: CustomerDTO,
    val listOfTicket: Set<TicketDTO>
)

fun Customer.toCustomerWithTicketsDTO(): CustomerWithTicketsDTO {
    return CustomerWithTicketsDTO( toDTO(), listOfTicket.map { it.toTicketDTO() }.toSet() )
}