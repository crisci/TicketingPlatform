package it.polito.wa2.ticketing.signup

import it.polito.wa2.ticketing.customer.CustomerDTO


interface SignupService {

    fun signupCustomer(credentials: Map<String, String>)

}