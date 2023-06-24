package it.polito.wa2.ticketing.customer

import com.fasterxml.jackson.annotation.JsonIgnore
import it.polito.wa2.ticketing.product.Product
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "customers")
class Customer {
    @Id
    @NotNull
    var id: UUID = UUID.randomUUID()
    @NotNull
    var first_name: String = ""
    @NotNull
    var last_name: String = ""
    @Column(unique = true)
    @NotNull
    var email: String = ""
    @NotNull
    var dob: LocalDate? = null
    @NotNull
    var address: String = ""
    @NotNull
    var phone_number: String = ""
    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var listOfTicket: MutableSet<Ticket> = mutableSetOf()
    @ManyToMany
    @JoinTable(
        name = "customer_product",
        joinColumns = [JoinColumn(name = "customer_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableSet<Product> = mutableSetOf()

    fun addProduct(product: Product) {
        products.add(product)
        product.customers.add(this)
    }

    fun create(id: UUID, first_name: String, last_name: String, email: String, dob: LocalDate, address: String, phone_number: String): Customer {
        val c = Customer()
        c.id = id
        c.first_name = first_name
        c.last_name = last_name
        c.email = email
        c.dob = dob
        c.address = address
        c.phone_number = phone_number
        return c
    }
    fun addTicket(ticket: Ticket) {
        ticket.customer = this
        listOfTicket.add(ticket)
    }



}

