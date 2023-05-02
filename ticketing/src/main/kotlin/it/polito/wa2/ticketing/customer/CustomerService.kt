package it.polito.wa2.ticketing.Cusotmer

interface CustomerService {

    fun getCustomer(id: Long): CustomerDTO?

}