package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.toDTO
import java.time.LocalDateTime

data class MessageDTO(
    val id: Long?,
    val body: String,
    val date: LocalDateTime?,
    val listOfAttachments: MutableSet<AttachmentDTO>? = null,
    val expert: Long? = null,
    val listOfAttachments: Set<AttachmentDTO>
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(getId(), body,date, listOfAttachment.map { it.toDTO() }.toMutableSet(), expert?.getId())
}



