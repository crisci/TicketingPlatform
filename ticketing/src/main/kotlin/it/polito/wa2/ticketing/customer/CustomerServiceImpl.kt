package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.ticket.TicketDTO

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service @Transactional
class CustomerServiceImpl(private val repository: CustomerRepository): CustomerService {
    override fun getCustomer(id: Long): CustomerDTO? {
        return repository.findById(id).orElseThrow {
            CustomerNotFoundException("Customer not found with the following id '${id}'")
        }
            .toDTO()
    }

    override fun getCustomerTickets(id: Long): Set<TicketDTO>? {
        return repository.findById(id).orElseThrow {
            CustomerNotFoundException("Customer not found with the following id '${id}'")
        }?.toCustomerWithTicketsDTO()?.listOfTicket
    }

    override fun getByCustomerTicketMessages(idCustomer: Long, idTicket: Long): Set<MessageDTO>? {
        return repository.findById(idCustomer).orElseThrow {
            CustomerNotFoundException("Customer not found with the following id '${idCustomer}'")
        }?.listOfTicket?.find { it.getId() == idTicket }?.listOfMessage?.map { it.toDTO() }?.toSet()
    }

    override fun getCustomerByEmail(email: String): CustomerDTO? {
        return repository.findByEmail(email)?.toDTO()
            ?: throw CustomerNotFoundException("Customer not found with the following email '${email}'")
    }

    override fun getCustomers(): List<CustomerDTO> {
        return repository.findAll().map { it.toDTO() }
    }

    override fun insertCustomer(customerDTO: CustomerDTO) {
        if (repository.findByEmail(customerDTO.email) != null)
            throw DuplicatedEmailException("${customerDTO.email.lowercase()} is already used")
        repository.save(
            Customer()
                .create(
                    customerDTO.first_name,
                    customerDTO.last_name,
                    customerDTO.email,
                    customerDTO.dob!!,
                    customerDTO.address,
                    customerDTO.phone_number
                )
        )
    }
}



