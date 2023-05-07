package it.polito.wa2.ticketing.product

import com.fasterxml.jackson.annotation.JsonIgnore
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product {
    @Id
    var ean: String = ""
    var name: String = ""
    var brand: String = ""
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfTicket: MutableSet<Ticket> = mutableSetOf()

    fun addTicket(t: Ticket){
        t.product = this
        listOfTicket.add(t)
    }
}
