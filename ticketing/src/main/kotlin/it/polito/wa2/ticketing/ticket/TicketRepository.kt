package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.history.History
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketRepository: JpaRepository<Ticket, Long> {

    fun findTicketsByCustomerId(customerId: UUID): Set<Ticket>

    @Query("SELECT t FROM Ticket t, History h WHERE t.history=h AND (h.ticket.id, h.date) IN (SELECT h2.ticket.id, MAX(h2.date) FROM History h2 GROUP BY h2.ticket.id) AND h.employee.id = :expertId")
    fun findTicketByMostRecentExpert(expertId: UUID): Set<Ticket?>
}