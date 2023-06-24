package it.polito.wa2.ticketing.attachment

import java.io.File
import java.sql.Blob

data class AttachmentDTO(
    val id: Long?,
    val attachment: ByteArray?

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AttachmentDTO

        if (id != other.id) return false
        if (attachment != null) {
            if (other.attachment == null) return false
            if (!attachment.contentEquals(other.attachment)) return false
        } else if (other.attachment != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (attachment?.contentHashCode() ?: 0)
        return result
    }
}

fun Attachment.toDTO(): AttachmentDTO {
    return AttachmentDTO(getId(), attachment)
}