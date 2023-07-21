package it.polito.wa2.ticketing.ticket

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeDTO
import it.polito.wa2.ticketing.history.HistoryDTO
import it.polito.wa2.ticketing.utils.TicketStatus
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@Observed
class TicketController(val ticketService: TicketService) {

    /*@GetMapping("/API/tickets/{idCustomer}/messages")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Manager")
    fun getTicketsWithMessagesByCustomerId(@PathVariable idCustomer: UUID): Set<TicketWithMessagesDTO>? {
        return ticketService.getTicketsWithMessagesByCustomerId(idCustomer)
    }*/

    @PutMapping("/API/tickets/{ticketId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun ticketClosing(@PathVariable ticketId: Long) {
        //ToDo("check if the client or the expert that are trying to do this are the right ones")
        return ticketService.closeTicket(ticketId)
    }

    @GetMapping("/API/tickets/{ticketId}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getMessages(@PathVariable ticketId : Long): List<MessageDTO> {
        //ToDo("check if the client or the expert that are trying to do this are the right ones")
        return ticketService.getMessages(ticketId)
    }

    @GetMapping("/API/tickets/{ticketId}/history")
    @ResponseStatus(HttpStatus.OK)
    fun getHistory(@PathVariable ticketId : Long): List<HistoryDTO> {
        //ToDo("check if the client or the expert that are trying to do this are the right ones")
        return ticketService.getHistory(ticketId)
    }

    @GetMapping("/API/tickets/{ticketId}/status")
    @ResponseStatus(HttpStatus.OK)
    fun getStatus(@PathVariable ticketId: Long): TicketStatus {
        //ToDo("check if the client or the expert that are trying to do this are the right ones")
        return ticketService.getStatus(ticketId)
    }

    @GetMapping("/API/tickets/{ticketId}/expert")
    @ResponseStatus(HttpStatus.OK)
    fun getCurrentExpert(@PathVariable ticketId: Long) : EmployeeDTO? {
        return ticketService.getExpert(ticketId)
    }
}
