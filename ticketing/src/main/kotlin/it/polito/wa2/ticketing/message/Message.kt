package it.polito.wa2.ticketing.message

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.utils.SenderType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="messages")
class Message: EntityBase<Long>(),Comparable<Message> {

    var type: SenderType? = null
    var body: String = ""
    var date: LocalDateTime = LocalDateTime.now()
    @JsonBackReference
    @OneToMany(mappedBy = "message", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfAttachment: MutableSet<Attachment>? = mutableSetOf()
    @JsonBackReference
    @ManyToOne
    var ticket: Ticket? = null
    @JsonBackReference
    @ManyToOne
    var expert: Employee? = null
    override fun compareTo(other: Message): Int {
        //most recent to oldest
        return -this.date.compareTo(other.date)
    }

}