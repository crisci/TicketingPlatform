package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.utils.TicketStatus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class TicketController(val ticketService: TicketService) {

    @GetMapping("/API/tickets/{idCustomer}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsWithMessagesByCustomerId(@PathVariable idCustomer: UUID): Set<TicketWithMessagesDTO>? {
        return ticketService.getTicketsWithMessagesByCustomerId(idCustomer)
    }

    @PutMapping("/API/tickets/{idExpert}/{ticketId}/stop")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun ticketMakeReassignable(@PathVariable("idExpert") idExpert: UUID, @PathVariable("ticketId") ticketId: Long) {
        //ToDo("check the idExpert")
        return ticketService.reassignTicket(ticketId, idExpert)
    }

    @PutMapping("/API/tickets/{idExpert}/{ticketId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //shared user and expert
    fun ticketClosing(@PathVariable("idExpert") idExpert: UUID, @PathVariable("ticketId") ticketId: Long) {
        return ticketService.closeTicket(ticketId, idExpert)
    }

    @GetMapping("/API/tickets/{idExpert}/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    //shared user and expert
    fun getMessages(@PathVariable("idExpert") idExpert : UUID, @PathVariable("ticketId") ticketId : Long): List<MessageDTO> {
        return ticketService.getMessages(ticketId, idExpert)
    }

    @GetMapping("/API/tickets/{ticketId}/status")
    @ResponseStatus(HttpStatus.OK)
    //shared user and expert
    fun getStatus(@PathVariable("ticketId") ticketId: Long): TicketStatus {
        //ToDo("check the idExpert")
        return ticketService.getStatus(ticketId)
    }


}