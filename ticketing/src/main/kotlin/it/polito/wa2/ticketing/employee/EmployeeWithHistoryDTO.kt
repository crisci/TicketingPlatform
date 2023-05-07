package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.history.HistoryDTO
import it.polito.wa2.ticketing.history.toDTO

data class EmployeeWithHistoryDTO(
    val employee: EmployeeDTO,
    val listOfHistory: Set<HistoryDTO>
)

fun Employee.toEmployeeWithHistoryDTO(): EmployeeWithHistoryDTO {
    return EmployeeWithHistoryDTO( toEmployeeDTO() ,listOfHistory.map { it.toDTO() }.toSet())
}