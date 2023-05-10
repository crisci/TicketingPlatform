package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import org.springframework.web.bind.annotation.RestController

@RestController
interface MessageService {

    fun getMessageAttachments(messageId: Long): Set<AttachmentDTO>?
    fun addAttachments(messageId: Long, attachments: Set<AttachmentDTO>)
    fun editMessage(messageId: Long, message: String)
    fun getMessagesByIdTickets(idTicket: Long): List<MessageDTO?>

}