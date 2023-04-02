package it.polito.wa2.server.products

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="products")
class Product {
    @Id
    lateinit var ean: String
    lateinit var name: String
    lateinit var brand: String


}