package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.ticket.TicketWithMessagesDTO

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service @Transactional
class CustomerServiceImpl(private val repository: CustomerRepository): CustomerService {
     override fun getCustomer(id: Long): CustomerDTO? {
        return repository.findById(id).orElse(null)?.toDTO()
     }

    override fun getCustomerTickets(id: Long): Set<TicketWithMessagesDTO>? {
        return repository.findById(id).orElse(null)?.toDTO()?.listOfTicket
    }
}