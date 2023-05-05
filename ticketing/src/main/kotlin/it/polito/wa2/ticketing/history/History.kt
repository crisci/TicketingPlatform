package it.polito.wa2.ticketing.history

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="histories")
class History : EntityBase<Long>(),Comparable<History> {
    var state: TicketStatus = TicketStatus.OPEN
    var date: LocalDateTime = LocalDateTime.now()
    @JsonBackReference
    @ManyToOne
    var ticket: Ticket? = null
    @JsonBackReference
    @ManyToOne
    var employee: Employee? = null

    fun create(state: TicketStatus,date: LocalDateTime,ticket: Ticket?,employee: Employee?):History{
        val h = History()
        h.state = state
        h.date = date
        h.ticket = ticket
        h.employee = employee
        return h
    }

    override fun compareTo(other: History): Int {
        //most recent to oldest
        return -this.date.compareTo(other.date)
    }

}