package it.polito.wa2.ticketing.employee

import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.message.Message
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="employees")
class Employee: EntityBase<Long>() {
    lateinit var email: String
    lateinit var password: String
    @Enumerated(EnumType.ORDINAL)
    lateinit var type: EmployeeRole
    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfHistory: MutableSet<History> = mutableSetOf()
    @JsonManagedReference
    @OneToMany(mappedBy = "expert", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfMessages: MutableSet<Message> = mutableSetOf()

}