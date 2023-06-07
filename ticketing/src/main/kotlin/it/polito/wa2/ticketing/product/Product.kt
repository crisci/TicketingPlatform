package it.polito.wa2.ticketing.product

import com.fasterxml.jackson.annotation.JsonIgnore
import it.polito.wa2.ticketing.customer.Customer
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "products")
class Product {
    @Id
    var ean: String = ""
    @NotNull
    var name: String = ""
    @NotNull
    var brand: String = ""
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfTicket: MutableSet<Ticket> = mutableSetOf()
    @ManyToMany(mappedBy = "products")
    val customers: MutableSet<Customer> = mutableSetOf()

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
