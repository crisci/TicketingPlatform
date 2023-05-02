package it.polito.wa2.ticketing.Attachment

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.Message.Message
import it.polito.wa2.ticketing.Utils.EntityBase
import jakarta.persistence.ManyToOne
import java.sql.Blob

class Attachment: EntityBase<Long>() {

    lateinit var attachment: Blob
    @JsonBackReference
    @ManyToOne
    var message: Message? = null

}