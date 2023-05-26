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
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
class MessageController(val messageService: MessageService) {

    @GetMapping("/API/messages/{messageId}/attachments", produces = ["image/jpeg", "image/png"])
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Expert", "ROLE_Client", "ROLE_Manager")
    fun getMessageAttachments(@PathVariable messageId: Long): Set<AttachmentDTO>{
        return messageService.getMessageAttachments(messageId)
    }
    @PostMapping("/API/messages/{messageId}/attachments")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_Expert", "ROLE_Client")
    fun addAttachments(@PathVariable messageId: Long, @RequestBody attachment: Array<MultipartFile>){
        messageService.addAttachment(messageId, attachment)
    }
    @GetMapping("/API/attachments", produces = ["image/jpeg", "image/png"])
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Expert", "ROLE_Client", "ROLE_Manager")
    fun getAttachment(@RequestParam id: Long): ByteArray {
        return messageService.getAttachment(id)
    }

    @GetMapping("/API/messages")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Expert", "ROLE_Client", "ROLE_Manager")
    fun getMessagesByIdTickets(@RequestParam("ticket") idTicket: Long): List<MessageDTO?> {
        return messageService.getMessagesByIdTickets(idTicket)
    }

    @PutMapping("/API/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Expert", "ROLE_Client")
    fun editMessage(@PathVariable messageId: Long, @RequestBody message: String){
        messageService.editMessage(messageId, message)
    }

}