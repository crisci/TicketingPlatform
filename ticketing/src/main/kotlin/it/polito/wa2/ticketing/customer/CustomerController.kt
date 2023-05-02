package it.polito.wa2.ticketing.Cusotmer

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(val customerService: CustomerService) {

    @GetMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomers(@PathVariable id: Long): CustomerDTO? {
        return customerService.getCustomer(id)
    }
}