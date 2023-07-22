package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.product.ProductDTO
import it.polito.wa2.ticketing.product.toDTO
import it.polito.wa2.ticketing.customer.CustomerDTO
import it.polito.wa2.ticketing.customer.toDTO
import it.polito.wa2.ticketing.employee.EmployeeDTO
import it.polito.wa2.ticketing.employee.toEmployeeDTO
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import java.time.LocalDateTime
import java.util.UUID

data class TicketDTO(
    val id: Long?,
    val title: String,
    val description: String?,
    val priority: PriorityLevel,
    val customer: CustomerDTO?,
    val product: ProductDTO?,
    val status: TicketStatus?,
    val openDate: LocalDateTime?,
    //val currentExpert: EmployeeDTO? = null
)

fun Ticket.toTicketDTO(): TicketDTO {
    return TicketDTO(id,title,description,priority,customer?.toDTO(), product?.toDTO(),
        history.maxByOrNull { it.date }?.state, history.minByOrNull { it.date }?.date
        //history.maxByOrNull { it.date }?.employee?.toEmployeeDTO()?
    )
}

fun Ticket.toTicketDTOExpertLastStatus(expertId: UUID): TicketDTO {
    return TicketDTO(id,title,description,priority,customer?.toDTO(), product?.toDTO(),
        history.filter { (it.employee?.id ?: false) == expertId }.maxByOrNull { it.date }?.state, history.minByOrNull { it.date }?.date
    )
}
