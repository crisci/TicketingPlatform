package it.polito.wa2.ticketing.message

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.attachment.Attachment
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="messages")
class Message: EntityBase<Long>() {

    lateinit var type: String
    lateinit var body: String
    lateinit var date: LocalDateTime
    @JsonManagedReference
    @OneToMany(mappedBy = "message", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfAttachment: MutableSet<Attachment> = mutableSetOf()
    @JsonBackReference
    @ManyToOne
    var ticket: Ticket? = null

}