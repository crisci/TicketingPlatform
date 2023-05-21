package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.security.JwtAuthConverter
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.utils.EmailValidationUtil
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreFilter
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController

class CustomerController(val customerService: CustomerService) {

    private val emailValidator = EmailValidationUtil()
    @GetMapping("/test/admin")
    @ResponseStatus(HttpStatus.OK)
    fun testCustomer(principal: Principal): String {
        val token = principal as JwtAuthenticationToken
        val userName = token.tokenAttributes["name"] as String?
        val userEmail = token.tokenAttributes["email"] as String?
        
        return ("Hello User \nUser Name : $userName\nUser Email : $userEmail\nUser Role: $userRole");
    }


    @GetMapping("/API/customers/id={id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomers(@PathVariable id: Long): CustomerDTO? {
        return customerService.getCustomer(id)
    }

    @GetMapping("/API/customers/{id}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTickets(@PathVariable id: Long): Set<TicketDTO>? {
        return customerService.getCustomerTickets(id)
    }

    @GetMapping("/API/customers/{idCustomer}/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerTicketWithMessages(@PathVariable idCustomer: Long, @PathVariable idTicket: Long): Set<MessageDTO>? {
        return customerService.getByCustomerTicketMessages(idCustomer, idTicket)
    }

    @GetMapping("/API/customers/email={email}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerByEmail(@PathVariable email: String) : CustomerDTO? {
        if (emailValidator.checkEmail(email)) {
            if(customerService.getCustomerByEmail(email.lowercase()) == null)
                throw CustomerNotFoundException("Customer not fount with the following email '${email}'")
            else
                return customerService.getCustomerByEmail(email.lowercase())
        } else {
            throw InvalidEmailFormatException("Invalid email format")
        }
    }

    @GetMapping("/API/customers/")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomers() : List<CustomerDTO> {
        return customerService.getCustomers()
    }

    @PostMapping("/API/customers")
    @ResponseStatus(HttpStatus.CREATED)
    fun postCustomer(@RequestBody customerWithPasswordDTO: CustomerWithPasswordDTO) {
        if(customerWithPasswordDTO.customer.email.isNotBlank() && customerWithPasswordDTO.customer.first_name.isNotBlank() && customerWithPasswordDTO.customer.last_name.isNotBlank()) {
            if (emailValidator.checkEmail(customerWithPasswordDTO.customer.email)) {
                if(customerWithPasswordDTO.password.length >= 8) {
                    customerService.insertCustomer(customerWithPasswordDTO)
                } else{
                throw PasswordTooShortException("Password must be at least 8 characters long")
            }} else {
                throw InvalidEmailFormatException("Invalid email format")
            }} else {
            throw BlankFieldsException("Fields must not be blank")
        }
    }

    @PutMapping("/API/customers/{email}") @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    fun putCustomer(@RequestBody customerWithPasswordDTO: CustomerWithPasswordDTO, @PathVariable(name = "email") email: String) {
        if(customerWithPasswordDTO.customer.email.isNotBlank() && customerWithPasswordDTO.customer.first_name.isNotBlank() && customerWithPasswordDTO.customer.last_name.isNotBlank()) {
            if (emailValidator.checkEmail(customerWithPasswordDTO.customer.email)) {
                if(customerWithPasswordDTO.password.length >= 8 ) {
                    customerService.updateCustomer(customerWithPasswordDTO, email.lowercase())
                } else {
                    throw PasswordTooShortException("Password must be at least 8 characters long")
                }} else {
                throw InvalidEmailFormatException("Invalid email format")
            }} else {
            throw BlankFieldsException("Fields must not be blank")
        }
    }


}