package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.ticket.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class ExpertController(val expertService: ExpertService) {
    @GetMapping("/API/expert/tickets")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Expert")
    fun getAssignedTickets(@RequestParam("expert") idExpert: UUID) : List<TicketDTO>{
        return expertService.getTickets(idExpert)
    }
    @PutMapping("/API/expert/{ticketId}/stop")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Expert")
    fun ticketMakeReassignable(@RequestParam("expert") idExpert: UUID, @PathVariable("ticketId") ticketId: Long) {
        return expertService.reassignTicket(ticketId, idExpert)
    }

}