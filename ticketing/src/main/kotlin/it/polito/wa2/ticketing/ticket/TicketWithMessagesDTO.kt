package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO

data class TicketWithMessagesDTO(
    val ticket: TicketDTO,
    val listOfMessage: Set<MessageDTO>?
)

fun Ticket.toTicketWithMessagesDTO(): TicketWithMessagesDTO {
    return TicketWithMessagesDTO( toTicketDTO() , listOfMessage.map { it.toDTO() }.toSet())
}
