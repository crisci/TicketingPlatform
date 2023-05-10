package it.polito.wa2.ticketing.history

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository: JpaRepository<History, Long> {

    fun findByTicketIdOrderByDateDesc(ticketId: Long): List<History>

    @Query("SELECT h FROM History h WHERE (h.ticket.id, h.date) IN (SELECT h2.ticket.id, MAX(h2.date) FROM History h2 GROUP BY h2.ticket.id)")
    fun findMostRecendStateByTicket(): List<History>

}