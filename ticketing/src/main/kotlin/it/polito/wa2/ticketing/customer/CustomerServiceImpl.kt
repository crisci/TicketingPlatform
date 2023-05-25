package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryNotFoundException
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.history.OperationNotPermittedException
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import it.polito.wa2.ticketing.utils.TicketStatus

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service @Transactional
class CustomerServiceImpl(
    private val repository: CustomerRepository,
    private val ticketRepository: TicketRepository,
    private val historyRepository: HistoryRepository
): CustomerService {
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
        if (repository.findByEmail(customerWithPasswordDTO.customer.email) != null)
            throw DuplicatedEmailException("${customerWithPasswordDTO.customer.email.lowercase()} is already used")
        repository.save(
            Customer()
                .create(
                    customerWithPasswordDTO.customer.first_name,
                    customerWithPasswordDTO.customer.last_name,
                    customerWithPasswordDTO.customer.email,
                    customerWithPasswordDTO.customer.dob!!,
                    customerWithPasswordDTO.password,
                    customerWithPasswordDTO.customer.address,
                    customerWithPasswordDTO.customer.phone_number
                )
        )
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
                }
            } else {
                throw PasswordMismatchException("The password is not correct")
            }
        } else {
            throw CustomerNotFoundException("Customer not fount with the following email '${email.lowercase()}'")
        }
    }

    override fun resolveTicket(ticketId: Long) {
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(ticketId)
        if (lastTicketHistory.isNotEmpty()) {
            val expert = lastTicketHistory.first().employee
            if (lastTicketHistory.first().state != TicketStatus.CLOSED && lastTicketHistory.first().state != TicketStatus.RESOLVED) {
                ticket.addHistory(
                    History().create(
                        TicketStatus.RESOLVED,
                        LocalDateTime.now(),
                        ticket,
                        expert //If null than the ticket is closed before than an expert is assigned
                    )
                )
            } else {
                throw OperationNotPermittedException("This operation is not permitted because the ticket is closed!")
            }
        } else {
            throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")
        }
    }

    override fun reopenTicket(ticketId: Long) {
        //Check the last history state related to the ticketId
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(ticketId)
        if (lastTicketHistory.isNotEmpty()) {
            if (lastTicketHistory.first().state == TicketStatus.CLOSED || lastTicketHistory.first().state == TicketStatus.RESOLVED) {
                ticket.addHistory(
                    History().create(
                        TicketStatus.OPEN,
                        LocalDateTime.now(),
                        ticket,
                        null
                    )
                )
            } else {
                throw OperationNotPermittedException("This operation is not permitted for the current ticket's state!")
            }
        } else {
            throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")
        }
    }

}