package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import it.polito.wa2.ticketing.attachment.AttachmentRepository
import it.polito.wa2.ticketing.utils.ImageUtil
import org.springframework.web.bind.annotation.*

@RestController
class MessageController(val messageService: MessageService, val messageRepository: MessageRepository, val attachmentRepository: AttachmentRepository) {

    @GetMapping("/API/messages/{messageId}/attachments")
    @ResponseStatus(HttpStatus.OK)
    fun getMessageAttachments(@PathVariable messageId: Long): Set<AttachmentDTO>{
        return messageService.getMessageAttachments(messageId)
    }

    @GetMapping("/API/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getMessagesByIdTickets(@RequestParam("ticket") idTicket: Long): List<MessageDTO?> {
        return messageService.getMessagesByIdTickets(idTicket)
    }

    @PostMapping("/API/messages/{messageId}/attachments")
    @ResponseStatus(HttpStatus.CREATED)
    fun addAttachments(@PathVariable messageId: Long, @RequestBody attachment: Array<MultipartFile>){
        messageService.addAttachment(messageId, attachment)
    }

    @PutMapping("/API/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    fun editMessage(@PathVariable messageId: Long, @RequestBody message: String){
        messageService.editMessage(messageId, message)
    }


    @PostMapping("/API/{messageId}/attachments")
    @ResponseStatus(HttpStatus.OK)
    fun test(@PathVariable messageId: Long, @RequestBody attachment: MultipartFile){
        attachmentRepository
           .save(Attachment().create(ImageUtil().compressImage(attachment.bytes), messageRepository.findById(messageId).get()))
    }

    @GetMapping("/API/{name}/attachments", produces = ["image/jpeg", "image/png"])
    @ResponseStatus(HttpStatus.OK)
    fun attGet(@PathVariable name: Long): ByteArray {
        val v = attachmentRepository.findById(name).get()
        val i = ImageUtil().decompressImage(v.attachment!!)
        return i!!
    }

}