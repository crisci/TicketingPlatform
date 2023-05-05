package it.polito.wa2.ticketing.ticket

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository: JpaRepository<Ticket, Long> {

    fun findTicketsByCustomerId(customerId: Long): Set<Ticket>

}