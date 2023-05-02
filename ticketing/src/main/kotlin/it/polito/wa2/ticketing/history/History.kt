package it.polito.wa2.ticketing.history

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.ticket.Ticket
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="histories")
class History: EntityBase<Long>() {

    lateinit var state: String
    lateinit var date: LocalDateTime
    @JsonBackReference
    @ManyToOne
    var ticket: Ticket? = null
    @JsonBackReference
    @ManyToOne
    var employee: Employee? = null

}