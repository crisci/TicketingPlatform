package it.polito.wa2.ticketing.message

import java.time.LocalDateTime

data class MessageDTO(
    val id: Long?,
    val body: String,
    val date: LocalDateTime?,
    val expert: Long? = null,
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(getId(), body,date,expert?.getId())
}



