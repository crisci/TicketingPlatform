package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.product.ProductDTO
import it.polito.wa2.ticketing.product.toDTO
import it.polito.wa2.ticketing.customer.CustomerDTO
import it.polito.wa2.ticketing.customer.toDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import java.time.LocalDateTime

data class TicketDTO(
    val id: Long?,
    val title: String,
    val description: String?,
    val priority: PriorityLevel,
    val customer: CustomerDTO?,
    val product: ProductDTO?,
    val status: TicketStatus?,
    val openDate: LocalDateTime?
)

fun Ticket.toTicketDTO(): TicketDTO {
    return TicketDTO(id,title,description,priority,customer?.toDTO(), product?.toDTO(), history.maxByOrNull { it.date }?.state, history.minByOrNull { it.date }?.date)
}
