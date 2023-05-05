package it.polito.wa2.ticketing.message

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.toDTO
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.utils.SenderType
import jakarta.persistence.CascadeType
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

data class MessageDTO(
    val id: Long?,
    val type: SenderType?,
    val body: String,
    val date: LocalDateTime,
    val listOfAttachment: Set<AttachmentDTO>? = setOf(),
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(getId(), type,body,date,listOfAttachment?.map { it.toDTO() }?.toSet())
}



