package it.polito.wa2.ticketing.employee

import com.fasterxml.jackson.annotation.JsonManagedReference
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.EntityBase
import it.polito.wa2.ticketing.history.History
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="employees")
class Employee: EntityBase<Long>() {

    var first_name: String = ""
    var last_name: String = ""
    var email: String = ""
    var password: String = ""
    @Enumerated(EnumType.ORDINAL)
    var type: EmployeeRole = EmployeeRole.EXPERT
    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var listOfHistory: MutableSet<History> = mutableSetOf()


}