package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.utils.EmployeeRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface EmployeeRepository: JpaRepository<Employee, UUID> {

    fun findByType(type: EmployeeRole): List<Employee>
    fun findByIdAndType(idExpert: UUID, type: EmployeeRole): Optional<Employee>
    fun findByEmail(email: String): Optional<Employee>
}