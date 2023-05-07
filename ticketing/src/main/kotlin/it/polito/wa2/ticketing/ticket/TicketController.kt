package it.polito.wa2.ticketing.ticket

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
    fun ticketMakeReassignable(@PathVariable("idExpert") idExpert: Long, @PathVariable("ticketId") ticketId: Long) {
        //ToDo("check the idExpert")
        return ticketService.reassignTicket(ticketId, idExpert)
    }

    @PutMapping("/tickets/{idExpert}/{ticketId}/close")
    //TODO: Who cares if the ticket is closed by the user or not?
    //Only a general method to close the ticket from both parties?
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun ticketCloseByExpert(@PathVariable("idExpert") idExpert: Long, @PathVariable("ticketId") ticketId: Long) {
        //ToDo("check the idExpert")
        return ticketService.closeTicket(ticketId, idExpert)
    }

    @GetMapping("/tickets/{idExpert}/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getMessages(@PathVariable("idExpert") idExpert : Long,@PathVariable("ticketId") ticketId : Long): List<MessageDTO> {
        //ToDo("check the idExpert")
        return ticketService.getMessages(ticketId, idExpert)
    }

    @GetMapping("/tickets/{idExpert}/{ticketId}/status")
    @ResponseStatus(HttpStatus.OK)
    fun getStatus(@PathVariable("idExpert") idExpert: Long, @PathVariable("ticketId") ticketId: Long): TicketStatus {
        //ToDo("check the idExpert")
        return ticketService.getStatus(ticketId, idExpert)
    }

    @PostMapping("/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMessage(@PathVariable idTicket: Long, @RequestBody message: MessageDTO) {
        ticketService.addMessage(idTicket, message)
    }

    @PostMapping("/tickets/{idCustomer}") //TODO: change with customers/{id}/tickets
    @ResponseStatus(HttpStatus.CREATED)
    fun addTicket(@RequestBody ticket: TicketDTO, @PathVariable idCustomer: Long) {
        ticketService.addTicket(ticket, idCustomer)
    }

    @PutMapping("/tickets/{idTicket}/resolve")
    fun ticketResolved(@PathVariable idTicket: Long) {
        ticketService.resolveTicket(idTicket)
    }

    @PutMapping("/tickets/{idTicket}/reopen")
    fun ticketReopen(@PathVariable idTicket: Long) {
        ticketService.reopenTicket(idTicket)
    }



}