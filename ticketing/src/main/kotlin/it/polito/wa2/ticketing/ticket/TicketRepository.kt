package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.history.History
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketRepository: JpaRepository<Ticket, Long> {

    fun findTicketsByCustomerId(customerId: UUID): Set<Ticket>

    @Query("SELECT t FROM Ticket t INNER JOIN History h WHERE h.state IN (1,2,3) AND h.employee.id=:expertId GROUP BY t.id")
    fun findTicketByMostRecentExpert(expertId: UUID): Set<Ticket>
}