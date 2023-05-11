package it.polito.wa2.ticketing.attachment

import java.sql.Blob

data class AttachmentDTO(
    val id: Long?,
    val attachment: ByteArray?
)

fun Attachment.toDTO(): AttachmentDTO {
    return AttachmentDTO(getId(), attachment)
}