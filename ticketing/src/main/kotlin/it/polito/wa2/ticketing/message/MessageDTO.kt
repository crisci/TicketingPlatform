package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.toDTO
import java.time.LocalDateTime

data class MessageDTO(
    val id: Long?,
    val body: String,
    val date: LocalDateTime?,
    val expert: Long? = null,
    val listOfAttachments: Set<AttachmentDTO>
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(getId(), body,date,expert?.getId(),listOfAttachment.map { it.toDTO() }.toSet())
}



