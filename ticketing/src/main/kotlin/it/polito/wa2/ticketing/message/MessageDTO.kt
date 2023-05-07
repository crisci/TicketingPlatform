package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.toDTO
import it.polito.wa2.ticketing.employee.EmployeeDTO
import it.polito.wa2.ticketing.employee.toEmployeeDTO
import it.polito.wa2.ticketing.utils.SenderType
import java.time.LocalDateTime

data class MessageDTO(
    val id: Long?,
    val type: SenderType?,
    val body: String,
    val date: LocalDateTime?,
    val listOfAttachment: Set<AttachmentDTO>? = setOf(),
    val expert: Long? = null,
)

fun Message.toDTO(): MessageDTO {
    return MessageDTO(getId(), type,body,date,listOfAttachment?.map { it.toDTO() }?.toSet(), expert?.getId())
}



