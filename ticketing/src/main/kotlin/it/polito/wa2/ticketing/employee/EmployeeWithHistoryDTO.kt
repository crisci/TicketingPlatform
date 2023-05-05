package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.history.HistoryDTO
import it.polito.wa2.ticketing.history.toDTO
import it.polito.wa2.ticketing.utils.EmployeeRole

data class EmployeeWithHistoryDTO(
    val id: Long?,
    val first_name: String,
    val last_name: String,
    val email: String,
    val type: EmployeeRole,
    val listOfHistory: Set<HistoryDTO>
)

fun Employee.toEmployeeWithHistoryDTO(): EmployeeWithHistoryDTO {
    return EmployeeWithHistoryDTO(getId(),first_name,last_name,email,type,listOfHistory.map { it.toDTO() }.toSet())
}