package it.polito.wa2.ticketing.history

import com.fasterxml.jackson.annotation.JsonBackReference
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeDTO
import it.polito.wa2.ticketing.employee.toDTO
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.toDTO
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

data class HistoryDTO(
    val id: Long?,
    val state: TicketStatus,
    val date: LocalDateTime,
    val ticket: TicketDTO?,
    val employee: EmployeeDTO?
)

fun History.toDTO(): HistoryDTO {
    return HistoryDTO(getId(), state,date,ticket?.toDTO(),employee?.toDTO())
}
