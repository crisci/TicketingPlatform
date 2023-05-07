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

    override fun insertCustomer(customerWithPasswordDTO: CustomerWithPasswordDTO) {
        repository.save(Customer().create(customerWithPasswordDTO.customer.first_name, customerWithPasswordDTO.customer.last_name, customerWithPasswordDTO.customer.email, customerWithPasswordDTO.customer.dob!!, customerWithPasswordDTO.password, customerWithPasswordDTO.customer.address, customerWithPasswordDTO.customer.phone_number))
    }


    //the body of the request must contain all the fields
    override fun updateCustomer(customerWithPasswordDTO: CustomerWithPasswordDTO, email: String) {
        val profileToUpdate: Customer? = repository
            .findByEmail(email)
            ?.also {
                it.email = customerWithPasswordDTO.customer.email
                it.first_name = customerWithPasswordDTO.customer.first_name
                it.last_name = customerWithPasswordDTO.customer.last_name
                it.phone_number = customerWithPasswordDTO.customer.phone_number
                it.address = customerWithPasswordDTO.customer.address
                it.dob = customerWithPasswordDTO.customer.dob
            }
        if (customerWithPasswordDTO.password != profileToUpdate?.password) {
            throw PasswordMismatchException("The password is not correct")
        }

        repository.save(profileToUpdate)
    }

}