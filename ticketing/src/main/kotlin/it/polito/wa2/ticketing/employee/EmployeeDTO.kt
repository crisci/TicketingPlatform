package it.polito.wa2.ticketing.employee

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import it.polito.wa2.ticketing.utils.EmployeeRole
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class EmployeeDTO(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val type: EmployeeRole? = null,
)


fun Employee.toEmployeeDTO(): EmployeeDTO {
    return EmployeeDTO(getId(),first_name,last_name,email,type)
}