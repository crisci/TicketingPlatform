package it.polito.wa2.ticketing.attachment

import java.io.File
import java.sql.Blob

data class AttachmentDTO(
    val id: Long?,
    val attachment: File?
)

fun Attachment.toDTO(): AttachmentDTO {
    return AttachmentDTO(getId(), attachment)
}