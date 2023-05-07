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

    override fun insertCustomer(customerWithPasswordDTO: CustomerWithPasswordDTO) {
        if(repository.findByEmail(customerWithPasswordDTO.customer.email) != null)
            throw DuplicatedEmailException("${customerWithPasswordDTO.customer.email.lowercase()} is already used")
        repository.save(Customer()
            .create(customerWithPasswordDTO.customer.first_name,
                customerWithPasswordDTO.customer.last_name,
                customerWithPasswordDTO.customer.email,
                customerWithPasswordDTO.customer.dob!!,
                customerWithPasswordDTO.password,
                customerWithPasswordDTO.customer.address,
                customerWithPasswordDTO.customer.phone_number))
    }


    //the body of the request must contain all the fields
    override fun updateCustomer(customerWithPasswordDTO: CustomerWithPasswordDTO, email: String) {
        println(repository.findByEmail(email)?.password)
            if (repository.findByEmail(email.lowercase()) != null) {
                if (customerWithPasswordDTO.password == repository.findByEmail(email)?.password) {
                if (repository.findByEmail(customerWithPasswordDTO.customer.email.lowercase()) == null) {
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
                    repository.save(profileToUpdate!!)
                } else {
                    throw DuplicatedEmailException("${customerWithPasswordDTO.customer.email.lowercase()} is already used")
                }} else {
                    throw PasswordMismatchException("The password is not correct")
                }} else {
                throw CustomerNotFoundException("Customer not fount with the following email '${email.lowercase()}'")
            }
        }

    }
