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
import java.util.UUID

@RestController
class TicketController(val ticketService: TicketService) {

    @GetMapping("/API/tickets/{idCustomer}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsWithMessagesByCustomerId(@PathVariable idCustomer: UUID): Set<TicketWithMessagesDTO>? {
        return ticketService.getTicketsWithMessagesByCustomerId(idCustomer)
    }

    @PutMapping("/API/tickets/{ticketId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun ticketClosing(@PathVariable("ticketId") ticketId: Long) {
        return ticketService.closeTicket(ticketId)
    }

    @GetMapping("/API/tickets/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getMessages(@PathVariable("ticketId") ticketId : Long): List<MessageDTO> {
        return ticketService.getMessages(ticketId)
    }

    @GetMapping("/API/tickets/{ticketId}/status")
    @ResponseStatus(HttpStatus.OK)
    fun getStatus(@PathVariable("ticketId") ticketId: Long): TicketStatus {
        return ticketService.getStatus(ticketId)
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



}