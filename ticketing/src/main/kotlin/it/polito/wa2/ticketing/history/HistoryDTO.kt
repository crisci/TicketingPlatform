package it.polito.wa2.ticketing.history

import it.polito.wa2.ticketing.employee.EmployeeDTO
import it.polito.wa2.ticketing.employee.toDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.toTicketDTO
import it.polito.wa2.ticketing.utils.TicketStatus
import java.time.LocalDateTime

data class HistoryDTO(
    val id: Long?,
    val state: TicketStatus,
    val date: LocalDateTime,
    val ticket: TicketDTO?,
    val employee: EmployeeDTO?
)

fun History.toDTO(): HistoryDTO {
    return HistoryDTO(getId(), state,date,ticket?.toTicketDTO(),employee?.toDTO())
}
