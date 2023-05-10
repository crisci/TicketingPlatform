package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.attachment.AttachmentRepository
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
    fun test(@PathVariable messageId: Long, @ModelAttribute("fileUpload") attachment: MultipartFile) {
        val convertedFile = File(attachment.originalFilename ?: "tempfile")
        try {
            attachment.transferTo(convertedFile)
            val att = Attachment().create(convertedFile, messageRepository.findById(1).get())
            attamentRepository.save(att)
        } catch (e: IOException) {
            // Handle the exception according to your needs
            throw e
        }
    }

}