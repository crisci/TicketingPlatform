package it.polito.wa2.ticketing.history

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository: JpaRepository<History, Long> {

    fun findByTicketIdOrderByDateDesc(ticketId: Long): List<History>
}