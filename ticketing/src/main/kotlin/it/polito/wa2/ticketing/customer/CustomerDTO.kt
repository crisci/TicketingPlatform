package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.ticket.Ticket
import java.time.LocalDate

data class CustomerDTO(
    var first_name: String,
    var last_name: String,
    var email: String,
    var dob: LocalDate?,
    var password: String,
    var address: String,
    var phone_number: String,
    var listOfTicket: MutableSet<Ticket> = mutableSetOf()
)

fun Customer.toDTO(): CustomerDTO {
    return CustomerDTO(first_name,last_name,email,dob,password,address,phone_number,listOfTicket)
}