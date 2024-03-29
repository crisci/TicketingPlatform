package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.toDTO
import java.time.LocalDateTime
import java.util.UUID

data class MessageDTO(
    val id: Long?,
    val body: String,
    val date: LocalDateTime?,
    val listOfAttachments: MutableSet<AttachmentDTO>? = null,
    val expert: UUID? = null,
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(getId(), body,date, listOfAttachment.map { it.toDTO() }.toMutableSet(), expert?.id)
}



