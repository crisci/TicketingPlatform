package it.polito.wa2.ticketing.ticket

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.customer.Customer
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.product.Product
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.utils.PriorityLevel
import jakarta.persistence.*

@Entity
@Table(name = "tickets")
class Ticket : EntityBase<Long>() {
    var title: String = ""
    var description: String = ""
    var priority: PriorityLevel = PriorityLevel.NOT_ASSIGNED
    @JsonBackReference
    @ManyToOne
    var customer: Customer? = null
    @JsonManagedReference
    @ManyToOne
    var product: Product? = null
    @JsonManagedReference
    @OneToMany(mappedBy="ticket", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var history: MutableSet<History> = mutableSetOf()

    fun addHistory(h: History){
        h.ticket = this
        history.add(h)
    }

}