package it.polito.wa2.ticketing.customer

import java.time.LocalDate
import java.util.UUID

data class CustomerDTO(
    val id: UUID?,
    val first_name: String,
    val last_name: String,
    val email: String,
    val dob: LocalDate?,
    val address: String,
    val phone_number: String,
)

fun Customer.toDTO(): CustomerDTO {
    return CustomerDTO(getId(),first_name,last_name,email,dob,address,phone_number)
}