package it.polito.wa2.ticketing.attachment

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.utils.EntityBase
import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne


@Entity
class Attachment: EntityBase<Long>() {
    @Lob
    var attachment: String? = null
    @JsonBackReference
    @ManyToOne
    var message: Message? = null

    fun create(attachment: String?, message: Message?): Attachment{
        val a = Attachment()
        a.attachment = attachment
        a.message = message
        return a
    }

}