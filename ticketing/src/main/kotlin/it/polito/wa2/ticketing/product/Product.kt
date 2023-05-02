package it.polito.wa2.ticketing.product

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product {
    @Id
    lateinit var ean: String
    lateinit var name: String
    lateinit var brand: String
    @JsonBackReference
    @OneToMany(mappedBy = "product")
    lateinit var listOfTicket: MutableSet<Ticket>
}
