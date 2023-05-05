package it.polito.wa2.ticketing.message

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.utils.SenderType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="messages")
class Message: EntityBase<Long>() {

    var type: SenderType? = null
    var body: String = ""
    var date: LocalDateTime = LocalDateTime.now()
    @JsonBackReference
    @OneToMany(mappedBy = "message", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfAttachment: MutableSet<Attachment>? = mutableSetOf()
    @JsonBackReference
    @ManyToOne
    var ticket: Ticket? = null

}