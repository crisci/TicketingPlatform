package it.polito.wa2.ticketing.attachment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AttachmentRepository: JpaRepository<Attachment, Long> {

    fun findAttachmentsByMessageId(messageId: Long): Set<Attachment>

    fun findByName(name: String): Optional<Attachment>

}