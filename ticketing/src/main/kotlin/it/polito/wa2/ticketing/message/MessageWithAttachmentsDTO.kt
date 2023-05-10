package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.toDTO

data class MessageWithAttachmentsDTO(
    val message: MessageDTO,
    val listOfAttachment: Set<AttachmentDTO>?
)

fun Message.toMessageWithAttachmentsDTO(): MessageWithAttachmentsDTO {
    return MessageWithAttachmentsDTO( toDTO() , listOfAttachment.map { it.toDTO() }.toSet() )
}
