package it.polito.wa2.ticketing.ticket

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.customer.Customer
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.product.Product
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.utils.PriorityLevel
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired

@Entity
@Table(name = "tickets")
class Ticket : EntityBase<Long>() {
    @Id
    var id:Long? = null
    @NotNull
    var title: String = ""
    @Lob
    var description: String? = ""
    var priority: PriorityLevel = PriorityLevel.NOT_ASSIGNED
    @JsonIgnore
    @ManyToOne
    var customer: Customer? = null
    @JsonIgnore
    @ManyToOne
    var product: Product? = null
    @JsonManagedReference
    @OneToMany(mappedBy="ticket", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var history: MutableSet<History> = mutableSetOf()
    @OneToMany(mappedBy = "ticket", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfMessage: MutableSet<Message> = mutableSetOf()

    fun create(title: String, description: String?, priority: PriorityLevel, customer: Customer, product: Product): Ticket {
        val t = Ticket()
        t.title = title
        t.description = description
        t.priority = priority
        t.customer = customer
        t.product = product
        return t
    }

    fun addHistory(h: History){
        h.ticket = this
        history.add(h)
    }

    fun addMessage(m: Message) {
        m.ticket = this
        listOfMessage.add(m)
    }


}