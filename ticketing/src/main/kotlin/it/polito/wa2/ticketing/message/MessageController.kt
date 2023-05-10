package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class MessageController(val messageService: MessageService) {

    @GetMapping("/API/messages/{messageId}/attachments")
    fun getMessageAttachments(@PathVariable messageId: Long): Set<AttachmentDTO>?{
        return messageService.getMessageAttachments(messageId)
    }

    @GetMapping("/API/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getMessagesByIdTickets(@RequestParam("ticket") idTicket: Long): List<MessageDTO?> {
        return messageService.getMessagesByIdTickets(idTicket)
    }

    @PostMapping("/API/messages/{messageId}/attachments")
    fun addAttachments(@PathVariable messageId: Long, @RequestBody attachments: Set<AttachmentDTO>){
        messageService.addAttachments(messageId, attachments)
    }

    @PutMapping("/API/messages/{messageId}")
    fun editMessage(@PathVariable messageId: Long, @RequestBody message: String){
        messageService.editMessage(messageId, message)
    }

}