package it.polito.wa2.ticketing.employee

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Observed
class ExpertController(val expertService: ExpertService) {
    @GetMapping("/API/expert/tickets")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun getAssignedTickets() : List<TicketDTO?>{
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        return expertService.getTickets(UUID.fromString(userDetails.tokenAttributes["sub"].toString()))
    }

    @PostMapping("/API/expert/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMessage(@PathVariable idTicket: Long, @RequestBody message: MessageDTO) {
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        expertService.addMessage(idTicket, message, UUID.fromString(userDetails.tokenAttributes["sub"].toString()))
    }

    @PutMapping("/API/expert/{ticketId}/stop")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Expert")
    fun ticketMakeReassignable(@PathVariable("ticketId") ticketId: Long) {
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        return expertService.reassignTicket(ticketId, UUID.fromString(userDetails.tokenAttributes["sub"].toString()))
    }

}
