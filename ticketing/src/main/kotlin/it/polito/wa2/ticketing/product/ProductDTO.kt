package it.polito.wa2.ticketing.product

import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.OneToMany

data class ProductDTO (
    var ean: String,
    var name: String,
    var brand: String,
    var listOfTicket: MutableSet<Ticket>
)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean,name,brand,listOfTicket)
}