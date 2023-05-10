package it.polito.wa2.ticketing.message

import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message, Long> {

    fun findMessagesByTicketId(ticketId: Long): Set<Message>

}