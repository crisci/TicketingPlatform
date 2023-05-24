package it.polito.wa2.ticketing.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerRepository: JpaRepository<Customer, UUID> {

    fun findByEmail(email: String): Customer?

}