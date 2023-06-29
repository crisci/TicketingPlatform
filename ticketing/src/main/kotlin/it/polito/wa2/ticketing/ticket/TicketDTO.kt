package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.product.ProductDTO
import it.polito.wa2.ticketing.product.toDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import java.time.LocalDateTime

data class TicketDTO(
    val id: Long?,
    val dateTime: LocalDateTime?,
    val title: String,
    val description: String,
    val priority: PriorityLevel,
    val product: ProductDTO?,
    val status: TicketStatus?
)

fun Ticket.toTicketDTO(): TicketDTO {
    return TicketDTO(getId(), history.minByOrNull { it.date }?.date,title,description,priority, product?.toDTO(), history.maxByOrNull { it.date }?.state)
}
