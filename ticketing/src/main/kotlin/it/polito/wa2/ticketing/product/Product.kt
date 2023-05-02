package it.polito.wa2.ticketing.Product

import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.Ticket.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product {
    @Id
    var ean: String = ""
    var name: String = ""
    var brand: String = ""
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    var listOfTicket: MutableSet<Ticket> = mutableSetOf()
}
