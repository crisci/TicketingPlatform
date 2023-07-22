package it.polito.wa2.ticketing.message

import io.micrometer.observation.annotation.Observed
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
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@Observed
class MessageController(val messageService: MessageService) {

    @GetMapping("/API/messages")
    @ResponseStatus(HttpStatus.OK)
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
