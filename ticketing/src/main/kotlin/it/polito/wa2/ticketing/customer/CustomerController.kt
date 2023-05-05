package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController

class CustomerController(val customerService: CustomerService) {

    @GetMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomers(@PathVariable id: Long): CustomerDTO? {
        return customerService.getCustomer(id)
    }

    @GetMapping("/customers/{id}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTickets(@PathVariable id: Long): Set<TicketDTO>? {
        return customerService.getCustomerTickets(id)
    }

    @GetMapping("/customers/{idCustomer}/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTicketWithMessages(@PathVariable idCustomer: Long, @PathVariable idTicket: Long): Set<MessageDTO>? {
        return customerService.getByCustomerTicketMessages(idCustomer, idTicket)
    }


}