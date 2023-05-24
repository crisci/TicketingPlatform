package it.polito.wa2.ticketing.ticket

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketRepository: JpaRepository<Ticket, Long> {

    fun findTicketsByCustomerId(customerId: UUID): Set<Ticket>

}