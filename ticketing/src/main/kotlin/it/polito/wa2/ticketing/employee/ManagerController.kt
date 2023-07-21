package it.polito.wa2.ticketing.employee

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.ticketing.customer.CustomerDTO
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Observed
class ManagerController(
    private val managerService: ManagerService) {

    @GetMapping("/API/manager/tickets/{status}")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsByStatus(@PathVariable status: TicketStatus?): List<TicketDTO?> {
        return managerService.getTicketsByStatus(status)
    }

    @GetMapping("/API/manager/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getTickets(): List<TicketDTO?> {
        return managerService.getTickets()
    }

    @GetMapping("/API/manager/customer")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomer(@RequestBody idCustomer: UUID): CustomerDTO? {
        return managerService.getCustomer(idCustomer)
    }

    @GetMapping("/API/manager/expert")
    @ResponseStatus(HttpStatus.OK)
    fun getExpert(@RequestBody idExpert: UUID): EmployeeDTO? {
        return managerService.getExpert(idExpert)
    }

    @GetMapping("/API/manager/experts")
    @ResponseStatus(HttpStatus.OK)
    fun getExperts(): List<EmployeeDTO?> {
        return managerService.getExperts()
    }

    @GetMapping("/API/manager/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getTicketsWithMessagesByCustomerId(@PathVariable idTicket: Long): List<MessageDTO>? {
        return managerService.getTicketMessages(idTicket)
    }

    @PutMapping("/API/manager/tickets/{idTicket}/assign")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun ticketAssign(@PathVariable idTicket: Long, @RequestBody idExpert: UUID, @RequestBody priorityLevel: PriorityLevel) {
        managerService.assignTicket(idTicket, idExpert, priorityLevel)
    }

    @PutMapping("/API/manager/experts/{expertId}/approve")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun expertApprove(@PathVariable expertId: UUID){
        managerService.expertApprove(expertId)
    }

}
