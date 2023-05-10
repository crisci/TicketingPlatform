package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.AttachmentRepository
import it.polito.wa2.ticketing.utils.ImageUtil
import jdk.jfr.ContentType
import org.apache.tomcat.util.http.fileupload.FileUpload
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException


@RestController
class MessageController(val messageService: MessageService, val messageRepository: MessageRepository, val attamentRepository: AttachmentRepository) {

    @GetMapping("/API/messages/{messageId}/attachments")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageAttachments(@PathVariable messageId: Long): Set<AttachmentDTO>?{
        return messageService.getMessageAttachments(messageId)
    }

    @GetMapping("/API/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getMessagesByIdTickets(@RequestParam("ticket") idTicket: Long): List<MessageDTO?> {
        return messageService.getMessagesByIdTickets(idTicket)
    }

    @PostMapping("/API/messages/{messageId}/attachments")
    @ResponseStatus(HttpStatus.OK)
    fun addAttachments(@PathVariable messageId: Long, @RequestBody attachments: Set<AttachmentDTO>){
        messageService.addAttachments(messageId, attachments)
    }

    @PutMapping("/API/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    fun editMessage(@PathVariable messageId: Long, @RequestBody message: String){
        messageService.editMessage(messageId, message)
    }


    @PostMapping("/API/{messageId}/attachments")
    @ResponseStatus(HttpStatus.OK)
    fun test(@PathVariable messageId: Long, @RequestBody attachment: MultipartFile){
       attamentRepository
           .save(Attachment().create(ImageUtil().compressImage(attachment.bytes), messageRepository.findById(messageId).get()))
    }

    @GetMapping("/API/{name}/attachments", produces = ["image/jpeg", "image/png"])
    @ResponseStatus(HttpStatus.OK)
    fun attGet(@PathVariable name: Long): ByteArray {
        val v = attamentRepository.findById(name).get()
        val i = ImageUtil().decompressImage(v.attachment!!)
        return i!!
    }

}