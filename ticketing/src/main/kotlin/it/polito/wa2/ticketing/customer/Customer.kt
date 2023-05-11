package it.polito.wa2.ticketing.customer

import com.fasterxml.jackson.annotation.JsonIgnore
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity
@Table(name = "customers")
class Customer: EntityBase<Long>() {
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
    var password: String = ""
    @NotNull
    var address: String = ""
    @NotNull
    var phone_number: String = ""
    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var listOfTicket: MutableSet<Ticket> = mutableSetOf()

    fun create(first_name: String, last_name: String, email: String, dob: LocalDate, password: String, address: String, phone_number: String): Customer {
        val c = Customer()
        c.first_name = first_name
        c.last_name = last_name
        c.email = email
        c.dob = dob
        c.password = password
        c.address = address
        c.phone_number = phone_number
        return c
    }
    fun addTicket(ticket: Ticket) {
        ticket.customer = this
        listOfTicket.add(ticket)
    }



}

