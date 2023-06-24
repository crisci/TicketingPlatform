package it.polito.wa2.ticketing.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {

    fun findProductByEan(ean: String): Product?

}