package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
interface MessageService {

    fun getMessageAttachments(messageId: Long): Set<ByteArray>
    fun addAttachment(messageId: Long, attachment: MultipartFile)
    fun editMessage(messageId: Long, message: String)

}