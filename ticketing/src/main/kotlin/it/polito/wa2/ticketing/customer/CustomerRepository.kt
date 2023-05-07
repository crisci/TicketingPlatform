package it.polito.wa2.ticketing.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {

    fun findByEmail(email: String): Customer?

}