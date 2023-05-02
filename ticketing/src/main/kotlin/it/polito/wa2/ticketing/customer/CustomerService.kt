package it.polito.wa2.ticketing.customer

interface CustomerService {

    fun getCustomer(id: Long): CustomerDTO?

}