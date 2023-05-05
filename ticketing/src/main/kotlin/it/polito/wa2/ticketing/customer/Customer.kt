package it.polito.wa2.ticketing.customer

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "customers")
class Customer: EntityBase<Long>() {
    var first_name: String = ""
    var last_name: String = ""
    var email: String = ""
    var dob: LocalDate? = null
    var password: String = ""
    var address: String = ""
    var phone_number: String = ""
    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var listOfTicket: MutableSet<Ticket> = mutableSetOf()
}