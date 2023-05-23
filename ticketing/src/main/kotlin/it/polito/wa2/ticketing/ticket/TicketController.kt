package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.history.HistoryDTO
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class TicketController(val ticketService: TicketService) {
    @GetMapping("/API/tickets/{idCustomer}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsByCustomerId(@PathVariable idCustomer: Long): Set<TicketDTO>? {
        return ticketService.getTicketsByCustomerId(idCustomer)
    }

    @GetMapping("/API/tickets/{idCustomer}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsWithMessagesByCustomerId(@PathVariable idCustomer: Long): Set<TicketWithMessagesDTO>? {
        return ticketService.getTicketsWithMessagesByCustomerId(idCustomer)
    }

    @PutMapping("/API/tickets/{idExpert}/{ticketId}/stop")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun ticketMakeReassignable(@PathVariable("idExpert") idExpert: Long, @PathVariable("ticketId") ticketId: Long) {
        //ToDo("check the idExpert")
        return ticketService.reassignTicket(ticketId, idExpert)
    }

    @PutMapping("/API/tickets/{idExpert}/{ticketId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //shared user and expert
    fun ticketClosing(@PathVariable("idExpert") idExpert: Long, @PathVariable("ticketId") ticketId: Long) {
        return ticketService.closeTicket(ticketId, idExpert)
    }

    @GetMapping("/API/tickets/{idExpert}/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    //shared user and expert
    fun getMessages(@PathVariable("idExpert") idExpert : Long,@PathVariable("ticketId") ticketId : Long): List<MessageDTO> {
        return ticketService.getMessages(ticketId, idExpert)
    }

    @GetMapping("/API/tickets/{ticketId}/status")
    @ResponseStatus(HttpStatus.OK)
    //shared user and expert
    fun getStatus(@PathVariable("ticketId") ticketId: Long): TicketStatus {
        //ToDo("check the idExpert")
        return ticketService.getStatus(ticketId)
    }

    @PostMapping("/API/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMessage(@PathVariable idTicket: Long, @RequestBody message: MessageDTO) {
        ticketService.addMessage(idTicket, message)
    }

    @PostMapping("/API/tickets/{idCustomer}") //TODO: change with customers/{id}/tickets
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun addTicket(@RequestBody ticket: TicketDTO, @PathVariable idCustomer: Long) {
        ticketService.addTicket(ticket, idCustomer)
    }

    @PutMapping("/API/tickets/{idTicket}/resolved")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun ticketResolved(@PathVariable idTicket: Long) {
        ticketService.resolveTicket(idTicket)
    }

    @PutMapping("/API/tickets/{idTicket}/reopen")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun ticketReopen(@PathVariable idTicket: Long) {
        ticketService.reopenTicket(idTicket)
    }

    @PutMapping("/API/tickets/{idTicket}/assign")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun ticketAssign(@PathVariable idTicket: Long, @RequestParam("expert") idExpert: Long, @RequestParam("priority") priorityLevel: PriorityLevel) {
        ticketService.assignTicket(idTicket, idExpert, priorityLevel)
    }

    @GetMapping("/API/tickets/status={status}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsByStatus(@PathVariable(required = false) status: TicketStatus?): Set<TicketDTO?> {
        return ticketService.getTicketsByStatus(status)
    }


}