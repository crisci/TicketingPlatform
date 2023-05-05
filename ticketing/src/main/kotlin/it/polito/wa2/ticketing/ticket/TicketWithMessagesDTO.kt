package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.product.ProductDTO
import it.polito.wa2.ticketing.product.toDTO
import it.polito.wa2.ticketing.utils.PriorityLevel

data class TicketWithMessagesDTO(
    val id: Long?,
    val title: String,
    val description: String,
    val priority: PriorityLevel,
    val product: ProductDTO?,
    val listOfMessage: Set<MessageDTO>?
)

fun Ticket.toTicketWithMessagesDTO(): TicketWithMessagesDTO {
    return TicketWithMessagesDTO(getId(), title,description,priority, product?.toDTO(), listOfMessage?.map { it.toDTO() }?.toSet())
}
