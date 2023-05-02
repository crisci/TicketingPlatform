package it.polito.wa2.ticketing.Ticket

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.Cusotmer.Customer
import it.polito.wa2.ticketing.Product.Product
import it.polito.wa2.ticketing.Utils.EntityBase
import it.polito.wa2.ticketing.Utils.PriorityLevel
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tickets")
class Ticket: EntityBase<Long>() {
    var title: String = ""
    var description: String = ""
    var priority: PriorityLevel = PriorityLevel.NOT_ASSIGNED
    @JsonBackReference
    @ManyToOne
    var customer: Customer? = null
    @JsonBackReference
    @ManyToOne
    var product: Product? = null
}