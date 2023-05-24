package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.ExpertNotFoundException
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryNotFoundException
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.history.OperationNotPermittedException
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.product.ProductNotFoundException
import it.polito.wa2.ticketing.product.ProductRepository
import it.polito.wa2.ticketing.ticket.*
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID


@Service @Transactional
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val ticketRepository: TicketRepository,
    private val historyRepository: HistoryRepository,
    private val productRepository: ProductRepository): CustomerService {



    override fun getTicketsWithMessagesByCustomerId(idCustomer: UUID, idTicket: Long): List<MessageDTO>? {
        if(!customerRepository.existsById(idCustomer))
            throw CustomerNotFoundException("No customer found with specified id!")
        return ticketRepository.findTicketsByCustomerId(idCustomer).find { it.getId() == idTicket }?.listOfMessage?.map { it.toDTO() }
    }


    override fun getCustomerByEmail(email: String): CustomerDTO? {
        return customerRepository.findByEmail(email)?.toDTO()
            ?: throw CustomerNotFoundException("Customer not found with the following email '${email}'")
    }

    override fun getCustomers(): List<CustomerDTO> {
        return customerRepository.findAll().map { it.toDTO() }
    }

    override fun insertCustomer(customerDTO: CustomerDTO) {
        if (customerRepository.findByEmail(customerDTO.email) != null)
            throw DuplicatedEmailException("${customerDTO.email.lowercase()} is already used")
        customerRepository.save(
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


    override fun getTicketsByCustomerId(customerId: UUID): List<TicketDTO> {
        if(!customerRepository.existsById(customerId))
            throw CustomerNotFoundException("No customer found with specified id!")
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketDTO() }
    }

    override fun addMessage(idTicket: Long, message: MessageDTO) {
        ticketRepository.findById(idTicket)
            .ifPresentOrElse(
                {
                    val histories = historyRepository.findByTicketIdOrderByDateDesc(idTicket)
                    if (histories.isNotEmpty() && histories
                            .first().state == TicketStatus.IN_PROGRESS
                    ) {
                        val newMessage = Message().create(
                            message.body,
                            LocalDateTime.now(),
                            mutableSetOf(), //Mutable set of attachments
                            it,
                            null //if not expert it remains null otherwise it is set
                        )

                        message.listOfAttachments?.map { a -> Attachment().create(
                            a.attachment, newMessage
                        ) }?.toMutableSet()?.forEach { a -> newMessage.addAttachment(a) }

                        it.addMessage(
                            newMessage
                        )
                    } else
                        throw OperationNotPermittedException("The ticket is not in progress!")
                },
                { throw TicketNotFoundException("The specified ticket has not been found!") })
    }


    override fun addTicket(ticket: TicketDTO, idCustomer: UUID) {
        customerRepository.findById(idCustomer)
            .ifPresentOrElse(
                {
                    val product = productRepository.findProductByEan(ticket.product?.ean!!)
                        ?: throw ProductNotFoundException("The specified product has not been found!")
                    val newTicket = Ticket()
                        .create(ticket.title, ticket.description, PriorityLevel.NOT_ASSIGNED, it, product) // Priority level assigned by the admin
                    newTicket.addHistory(History().create(TicketStatus.OPEN, LocalDateTime.now(), newTicket, null))
                    //TODO: Setted by the admin -> newTicket.addHistory(History().create(TicketStatus.IN_PROGRESS, LocalDateTime.now(), newTicket, null))
                    it.addTicket(newTicket)
                },
                { throw CustomerNotFoundException("The specified customer has not been found!") }
            )

    }
}