package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.utils.EmailValidationUtil
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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

    private val emailValidator = EmailValidationUtil()

    @GetMapping("/API/customers/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getCustomerByEmail(@PathVariable email: String) : CustomerDTO? {
        if(customerService.getCustomerByEmail(email.lowercase()) == null)
            throw CustomerNotFoundException("Customer not fount with the following email '${email}'")
        else
            return customerService.getCustomerByEmail(email.lowercase())
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
                    if (customerService.getCustomerByEmail(customerWithPasswordDTO.customer.email.lowercase()) == null)
                        customerService.insertCustomer(customerWithPasswordDTO)
                    else {
                        throw DuplicatedEmailException("${customerWithPasswordDTO.customer.email.lowercase()} is already used")
                    }}
                else{
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
                    if (customerService.getCustomerByEmail(email.lowercase()) != null) {
                        if (customerService.getCustomerByEmail(customerWithPasswordDTO.customer.email.lowercase()) == null)
                            customerService.updateCustomer(customerWithPasswordDTO, email.lowercase())
                        else {
                            throw DuplicatedEmailException("${customerWithPasswordDTO.customer.email.lowercase()} is already used")
                    }} else {
                    throw CustomerNotFoundException("Customer not fount with the following email '${email.lowercase()}'")
                }} else {
                    throw PasswordTooShortException("Password must be at least 8 characters long")
                }} else {
                throw InvalidEmailFormatException("Invalid email format")
            }} else {
            throw BlankFieldsException("Fields must not be blank")
        }
    }


}