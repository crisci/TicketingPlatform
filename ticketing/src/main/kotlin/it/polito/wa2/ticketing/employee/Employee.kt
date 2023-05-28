package it.polito.wa2.ticketing.employee

import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
@Table(name="employees")
class Employee {
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
    @Enumerated(EnumType.ORDINAL)
    var type: EmployeeRole = EmployeeRole.EXPERT
    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfHistory: MutableSet<History> = mutableSetOf()
    @JsonManagedReference
    @OneToMany(mappedBy = "expert", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfMessages: MutableSet<Message> = mutableSetOf()

    fun createExpert(id:UUID, firstName: String, last_name: String, email: String): Employee {
        return Employee().apply {
            this.id = id
            this.first_name = firstName
            this.last_name = last_name
            this.email = email
            this.type = EmployeeRole.EXPERT
        }
    }

    fun addMessage(m: Message){
        m.expert = this
        listOfMessages.add(m)
    }

    fun addHistory(h: History){
        h.employee = this
        listOfHistory.add(h)
    }

}