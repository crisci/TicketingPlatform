package it.polito.wa2.ticketing.employee

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
class ManagerController(
    private val managerService: ManagerService) {

    @GetMapping("/API/manager/tickets")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Manager")
    fun getTicketsByStatus(@RequestParam("status") status: TicketStatus?): List<TicketDTO?> {
        return managerService.getTicketsByStatus(status)
    }

    @GetMapping("/API/manager/customers")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Manager")
    fun getCustomer(@RequestParam("id") idCustomer: UUID): CustomerDTO? {
        return managerService.getCustomer(idCustomer)
    }

    @GetMapping("/API/manager/experts")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Manager")
    fun getExpert(@RequestParam("id") idExpert: UUID): EmployeeDTO? {
        return managerService.getExpert(idExpert)
    }
    /*
        @GetMapping("/API/manager/experts/")
        @ResponseStatus(HttpStatus.OK)
        @Secured("ROLE_Manager")
        fun getExperts(): List<EmployeeDTO?> {
            return managerService.getExperts()
        }
    */
    @GetMapping("/API/manager/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Manager")
    fun getTicketsWithMessagesByCustomerId(@PathVariable idTicket: Long): List<MessageDTO>? {
        return managerService.getTicketMessages(idTicket)
    }

    @PutMapping("/API/manager/tickets/{idTicket}/assign")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Manager")
    fun ticketAssign(@PathVariable idTicket: Long, @RequestParam("expert") idExpert: UUID, @RequestParam("priority") priorityLevel: PriorityLevel) {
        managerService.assignTicket(idTicket, idExpert, priorityLevel)
    }


}