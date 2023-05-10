package it.polito.wa2.ticketing.message

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.employee.Employee
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="messages")
class Message: EntityBase<Long>(),Comparable<Message> {

    var body: String = ""
    var date: LocalDateTime = LocalDateTime.now()
    @OneToMany(mappedBy = "message", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfAttachment: MutableSet<Attachment> = mutableSetOf()
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


    fun create(body: String,date: LocalDateTime,listOfAttachment: MutableSet<Attachment>?,ticket: Ticket?,expert: Employee?):Message{
        val m = Message()
        m.body = body
        m.date = date
        if(listOfAttachment != null)
            m.listOfAttachment = listOfAttachment
        m.ticket = ticket
        m.expert = expert
        return m
    }

    fun addAttachment(a: Attachment){
        a.message = this
        listOfAttachment.add(a)
    }

}