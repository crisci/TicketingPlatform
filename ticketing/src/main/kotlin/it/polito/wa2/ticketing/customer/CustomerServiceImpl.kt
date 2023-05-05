package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.ticket.TicketDTO

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

    override fun getByCustomerTicketMessages(idCustomer: Long, idTicket: Long): Set<MessageDTO>? {
        return repository.findById(idCustomer).orElse(null)?.listOfTicket?.find { it.getId() == idTicket }?.listOfMessage?.map { it.toDTO() }?.toSet()
    }
}