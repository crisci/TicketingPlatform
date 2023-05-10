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

    fun create(ean: String, name: String, brand: String): Product {
        val p = Product()
        p.ean = ean
        p.name = name
        p.brand = brand
        return p
    }

    fun addTicket(t: Ticket){
        t.product = this
        listOfTicket.add(t)
    }
}
