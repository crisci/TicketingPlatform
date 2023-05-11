package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.utils.EmployeeRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository: JpaRepository<Employee, Long> {

    fun findByIdAndType(idExpert: Long, type: EmployeeRole): Optional<Employee>
}