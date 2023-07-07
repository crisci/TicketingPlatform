package it.polito.wa2.ticketing.signup

import it.polito.wa2.ticketing.customer.CustomerDTO
import org.springframework.http.ResponseEntity


interface SignupService {

    fun signupCustomer(credentials: Map<String, String>)
    fun createExpert(credentials: Map<String, String>)

    fun refresh(credentials: Map<String, String>): ResponseEntity<String>

}