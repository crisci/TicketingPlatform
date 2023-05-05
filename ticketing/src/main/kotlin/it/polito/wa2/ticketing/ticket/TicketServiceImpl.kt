package it.polito.wa2.ticketing.ticket

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service @Transactional
class TicketServiceImpl(private val repository: TicketRepository): TicketService {

    override fun getTicketsByCustomerId(customerId: Long): Set<TicketDTO>? {
        return repository.findTicketsByCustomerId(customerId).map { it.toDTO() }.toSet()
    }

}