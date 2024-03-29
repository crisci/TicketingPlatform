package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
interface MessageService {


    fun editMessage(messageId: Long, message: String)
    fun getMessagesByIdTickets(idTicket: Long): List<MessageDTO?>


}