package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.utils.TicketStatus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController(val ticketService: TicketService) {
    @GetMapping("/tickets/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsByCustomerId(@PathVariable id: Long): Set<TicketDTO>? {
        return ticketService.getTicketsByCustomerId(id)
    }

    @GetMapping("/tickets/{id}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsWithMessagesByCustomerId(@PathVariable id: Long): Set<TicketWithMessagesDTO>? {
        return ticketService.getTicketsWithMessagesByCustomerId(id)
    }
    @PutMapping("/tickets/{idExpert}/{ticketId}/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun ticketMakeReassignable(@PathVariable("idExpert") idExpert : Long,@PathVariable("ticketId") ticketId : Long){
        //ToDo("check the idExpert")
        return ticketService.reassignTicket(ticketId,idExpert)
    }
    @PutMapping("/tickets/{idExpert}/{ticketId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun ticketCloseByExpert(@PathVariable("idExpert") idExpert : Long,@PathVariable("ticketId") ticketId : Long){
        //ToDo("check the idExpert")
        return ticketService.closeTicket(ticketId,idExpert)
    }
    @GetMapping("/tickets/{idExpert}/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getMessages(@PathVariable("idExpert") idExpert : Long,@PathVariable("ticketId") ticketId : Long): List<MessageDTO> {
        //ToDo("check the idExpert")
        return ticketService.getMessages(ticketId,idExpert)
    }
    @GetMapping("/tickets/{idExpert}/{ticketId}/status")
    @ResponseStatus(HttpStatus.OK)
    fun getStatus(@PathVariable("idExpert") idExpert : Long,@PathVariable("ticketId") ticketId : Long): TicketStatus {
        //ToDo("check the idExpert")
        return ticketService.getStatus(ticketId,idExpert)
    }
}