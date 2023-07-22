package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.history.History
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketRepository: JpaRepository<Ticket, Long> {

    fun findTicketsByCustomerId(customerId: UUID): Set<Ticket>

    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.history h WHERE h.employee.id = :expertId")
    fun findTicketByMostRecentExpert(expertId: UUID): Set<Ticket?>
}
