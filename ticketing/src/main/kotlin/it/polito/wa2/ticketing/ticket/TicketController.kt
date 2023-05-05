package it.polito.wa2.ticketing.ticket

import org.springframework.http.HttpStatus
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

}