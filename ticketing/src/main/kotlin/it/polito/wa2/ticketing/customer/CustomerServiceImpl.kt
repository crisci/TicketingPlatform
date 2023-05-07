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
        return repository.findById(id).orElse(null)?.toCustomerWithTicketsDTO()?.listOfTicket
    }

    override fun getByCustomerTicketMessages(idCustomer: Long, idTicket: Long): Set<MessageDTO>? {
        return repository.findById(idCustomer).orElse(null)?.listOfTicket?.find { it.getId() == idTicket }?.listOfMessage?.map { it.toDTO() }?.toSet()
    }

    override fun getCustomerByEmail(email: String): CustomerDTO? {
        return repository.findByEmail(email)?.toDTO()
    }

    override fun getCustomers(): List<CustomerDTO> {
        return repository.findAll().map { it.toDTO() }
    }

    override fun insertCustomer(customerDTO: CustomerDTO) {
        repository.save(Customer().create(customerDTO.first_name, customerDTO.last_name, customerDTO.email, customerDTO.dob!!, "password", customerDTO.address, customerDTO.phone_number))
    }


    //the body of the request must contain all the fields
    override fun updateCustomer(customerDTO: CustomerDTO, email: String) {
        val profileToUpdate: Customer? = repository
            .findByEmail(email)
            ?.also {
                it.email = customerDTO.email
                it.first_name = customerDTO.first_name
                it.last_name = customerDTO.last_name
            }
        repository.save(profileToUpdate!!)
    }

}