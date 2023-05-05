package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.toDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service @Transactional
class CustomerServiceImpl(private val repository: CustomerRepository): CustomerService {
     override fun getCustomer(id: Long): CustomerDTO? {
        return repository.findById(id).orElse(null)?.toDTO()
     }

    override fun getCustomerTickets(id: Long): Set<TicketDTO>? {
        return repository.findById(id).orElse(null)?.toDTO()?.listOfTicket
    }
}