package it.polito.wa2.ticketing.customer

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.product.ProductDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.utils.EmailValidationUtil
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.http.*
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.util.UUID


@RestController
@Observed
class CustomerController(val customerService: CustomerService) {

    private val emailValidator = EmailValidationUtil()
    private data class CredentialRapresentation(
        val role: String,
        val password: String
    )
    private data class UserRapresentation(
        val username: String,
        val email:String,
        val firstName:String,
        val lastName:String,
        val credentials: CredentialRapresentation
        )

    @PostMapping("/API/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody credentials: Map<String, String>): ResponseEntity<String> {
        val restTemplate = RestTemplate()

        val url = "http://keycloak:8080/realms/ticketing/protocol/openid-connect/token"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val requestBody: MultiValueMap<String, String> = LinkedMultiValueMap()
        requestBody.add("grant_type", "password")
        requestBody.add("client_id", "authN")
        requestBody.add("username", credentials["username"])
        requestBody.add("password", credentials["password"])
        try {
            val requestEntity = HttpEntity(requestBody, headers)
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String::class.java)
        } catch (e: Exception) {
            throw LoginErrorException("Error during login")
        }
    }
    @GetMapping("/API/customers/email={email}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Expert", "ROLE_Manager")
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

    @GetMapping("/API/customers/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Customer")
    fun getTicketsWithMessagesByCustomerId(@PathVariable idTicket: Long): List<MessageDTO>? {
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        return customerService.getTicketsWithMessagesByCustomerId(UUID.fromString(userDetails.tokenAttributes["sub"].toString()), idTicket)
    }

    @PostMapping("/API/customers/tickets/{idTicket}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_Customer")
    fun addMessage(@PathVariable idTicket: Long, @RequestBody message: MessageDTO) {
        customerService.addMessage(idTicket, message)
    }

    @GetMapping("/API/customers/tickets")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_Customer")
    fun getTicketsByCustomerId(): List<TicketDTO>? {
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        return customerService.getTicketsByCustomerId(UUID.fromString(userDetails.tokenAttributes["sub"].toString()))
    }



    @PostMapping("/API/customers/tickets")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Customer")
    fun addTicket(@RequestBody ticket: TicketDTO) {
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        customerService.addTicket(ticket, UUID.fromString(userDetails.tokenAttributes["sub"].toString()))
    }
    @GetMapping("/API/customers/products")
    @ResponseStatus(HttpStatus.OK)
    fun getRegisteredProducts(): List<ProductDTO>? {
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        return customerService.getRegisteredProducts(UUID.fromString(userDetails.tokenAttributes["sub"].toString()))
    }
    @PostMapping("/API/customers/product")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_Customer")
    fun productRegistration(@RequestBody ean: String) {
        val userDetails = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
        customerService.productRegistration(UUID.fromString(userDetails.tokenAttributes["sub"].toString()), ean)
    }

    @PutMapping("/API/customers/tickets/{idTicket}/resolved")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Customer")
    fun ticketResolved(@PathVariable idTicket: Long) {
        customerService.resolveTicket(idTicket)
    }

    @PutMapping("/API/customers/tickets/{idTicket}/reopen")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Customer")
    fun ticketReopen(@PathVariable idTicket: Long) {
        customerService.reopenTicket(idTicket)
    }

    @PutMapping("/API/customers/tickets/{idTicket}/close")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured("ROLE_Customer")
    fun ticketClose(@PathVariable idTicket: Long) {
        customerService.reopenTicket(idTicket)
    }



}
