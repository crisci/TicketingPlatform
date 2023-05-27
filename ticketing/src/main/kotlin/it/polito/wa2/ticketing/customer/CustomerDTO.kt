package it.polito.wa2.ticketing.customer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class CustomerDTO(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dob: LocalDate?,
    val address: String,
    val phoneNumber: String,
)

fun Customer.toDTO(): CustomerDTO {
    return CustomerDTO(id,first_name,last_name,email,dob,address,phone_number)
}