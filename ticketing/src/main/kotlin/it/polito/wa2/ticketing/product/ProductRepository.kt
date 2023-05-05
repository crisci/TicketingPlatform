package it.polito.wa2.ticketing.product

import it.polito.wa2.ticketing.ticket.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Repository
interface ProductRepository: JpaRepository<Product, Long> {

    fun findProductByEan(ean: String): Product?

}