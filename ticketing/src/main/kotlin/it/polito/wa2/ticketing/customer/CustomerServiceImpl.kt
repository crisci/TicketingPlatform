package it.polito.wa2.ticketing.customer

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryNotFoundException
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.history.OperationNotPermittedException
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.message.toDTO
import it.polito.wa2.ticketing.product.*
import it.polito.wa2.ticketing.ticket.*
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus

import jakarta.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID


@Service @Transactional
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val ticketRepository: TicketRepository,
    private val historyRepository: HistoryRepository,
    private val productRepository: ProductRepository): CustomerService {


    @Secured("ROLE_Client")
    override fun getTicketsWithMessagesByCustomerId(idCustomer: UUID, idTicket: Long): List<MessageDTO>? {
        if(!customerRepository.existsById(idCustomer))
            throw CustomerNotFoundException("No customer found with specified id!")
        return ticketRepository.findTicketsByCustomerId(idCustomer).find { it.getId() == idTicket }?.listOfMessage?.map { it.toDTO() }
    }


    @Secured("ROLE_Manager", "ROLE_Expert")
    override fun getCustomerByEmail(email: String): CustomerDTO? {
        return customerRepository.findByEmail(email)?.toDTO()
            ?: throw CustomerNotFoundException("Customer not found with the following email '${email}'")
    }

    @Secured("ROLE_Manager")
    override fun getCustomers(): List<CustomerDTO> {
        return customerRepository.findAll().map { it.toDTO() }
    }

    @Secured("ROLE_Client")
    override fun getTicketsByCustomerId(customerId: UUID): List<TicketDTO> {
        if(!customerRepository.existsById(customerId))
            throw CustomerNotFoundException("No customer found with specified id!")
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketDTO() }
    }

    @Secured("ROLE_Client")
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

                        message.listOfAttachments?.map { a ->
                            Attachment().create(
                                a.attachment, newMessage
                            )
                        }?.toMutableSet()?.forEach { a -> newMessage.addAttachment(a) }

                        it.addMessage(
                            newMessage
                        )
                    } else
                        throw OperationNotPermittedException("The ticket is not in progress!")
                },
                { throw TicketNotFoundException("The specified ticket has not been found!") })
    }

    @Secured("ROLE_Client")
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


    @Secured("ROLE_Client")
    override fun addTicket(ticket: TicketDTO, idCustomer: UUID) {
        //verify that ticket title less than 55 and description less than 2048
        if ( ticket.title.length > 55 || ticket.description!=null && ticket.description.length > 1000)
            throw TicketNotValidException("The ticket title or description is not valid!")

        val customer = customerRepository.findById(idCustomer)
            .orElseThrow { CustomerNotFoundException("The specified customer has not been found!") }
        val product = productRepository.findProductByEan(ticket.product?.ean!!) ?: throw ProductNotFoundException("The specified product has not been found!")
        val products = customer.products
        if (!products.contains(product))
            throw ProductNotRegistered("The specified product is not registered to the customer!")
        val newTicket = Ticket()
            .create(ticket.title, ticket.description, ticket.priority, customer, product) // Priority level assigned by the admin
        newTicket.addHistory(History().create(TicketStatus.OPEN, LocalDateTime.now(), newTicket, null))
        customer.addTicket(newTicket)
    }

    @Secured("ROLE_Client")
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

    @Secured("ROLE_Client")
    override fun closeTicket(ticketId: Long) {
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(ticketId)
        println(ticket.toString())
        println(lastTicketHistory.toString())
        if (lastTicketHistory.isNotEmpty()) {
            println(lastTicketHistory.first().state.toString())
            if (lastTicketHistory.first().state != TicketStatus.CLOSED) {
                ticket.addHistory(
                    History().create(
                        TicketStatus.CLOSED,
                        LocalDateTime.now(),
                        ticket,
                        lastTicketHistory.first().employee
                    )
                )
            } else {
                throw OperationNotPermittedException("This operation is not permitted for the current ticket's state!")
            }
        } else {
            throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")
        }
    }

    @Secured("ROLE_Client")
    override fun productRegistration(customerId: UUID, ean: String) {
        val product = productRepository.findProductByEan(ean) ?: throw ProductNotFoundException("The specified product has not been found!")
        val customer = customerRepository.findById(customerId).orElseThrow { CustomerNotFoundException("The specified customer has not been found!") }
        val customProducts = customer.products
        if (customProducts.contains(product)) {
            throw ProductAlreadyRegisteredException("The specified product is already registered!")
        } else {
            customer.addProduct(product)
        }
    }

    @Secured("ROLE_Client")
    override fun getRegisteredProducts(customerId: UUID): List<ProductDTO>? {
        val customer = customerRepository.findById(customerId).orElseThrow { CustomerNotFoundException("The specified customer has not been found!") }
        return customer.products.map { it.toDTO() }
    }

    @Secured("ROLE_Client")
    override fun deleteProduct(customerId: UUID, ean: String) {
        val product = productRepository.findProductByEan(ean) ?: throw ProductNotFoundException("The specified product has not been found!")
        val customer = customerRepository.findById(customerId).orElseThrow { CustomerNotFoundException("The specified customer has not been found!") }
        val customProducts = customer.products
        if (customProducts.contains(product)) {
            customer.removeProduct(product)
        } else {
            throw ProductNotRegistered("The specified product is not registered!")
        }
    }

}

