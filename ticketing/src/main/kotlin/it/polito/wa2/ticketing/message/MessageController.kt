package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.AttachmentDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(val messageService: MessageService) {

    @GetMapping("/API/messages/{messageId}/attachments")
    fun getMessageAttachments(@PathVariable messageId: Long): Set<AttachmentDTO>?{
        return messageService.getMessageAttachments(messageId)
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