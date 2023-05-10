package it.polito.wa2.ticketing.ticket

import it.polito.wa2.ticketing.customer.CustomerNotFoundException
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeRepository
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
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service @Transactional
class TicketServiceImpl(private val ticketRepository: TicketRepository,
                        private val employeeRepository: EmployeeRepository,
                        private val historyRepository: HistoryRepository,
                        private val customerRepository: CustomerRepository,
                        private val productRepository: ProductRepository
): TicketService {
    override fun getTicketsWithMessagesByCustomerId(customerId: Long): Set<TicketWithMessagesDTO>? {
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketWithMessagesDTO() }.toSet()
    }

    override fun getTicketsByCustomerId(customerId: Long): Set<TicketDTO>? {
        return ticketRepository.findTicketsByCustomerId(customerId).map { it.toTicketDTO() }.toSet()
    }

    override fun reassignTicket(ticketId: Long, idExpert: Long) {
        ticketRepository.findById(ticketId).ifPresentOrElse(
            {
                if (historyRepository.findByTicketIdOrderByDateDesc(ticketId).first().state != TicketStatus.OPEN) {

                    var admin: Employee? = null
                    for (h: History in it.history.sorted()) {
                        if (h.employee == null || h.employee?.type == EmployeeRole.ADMIN) {
                            admin = h.employee
                            break
                        }
                    }
                    it.addHistory(History().create(TicketStatus.OPEN, LocalDateTime.now(), it, admin))
                    ticketRepository.save(it)
                } else {
                    throw OperationNotPermittedException("The ticket is still open!")
                }
            },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
        ticketRepository.flush()
    }

    override fun closeTicket(ticketId: Long, idExpert: Long) {
        val expert = employeeRepository.findByIdOrNull(idExpert)!!
        ticketRepository.findById(ticketId).ifPresentOrElse(
            { it.addHistory(History().create(TicketStatus.CLOSED, LocalDateTime.now(), it, expert)); ticketRepository.save(it) },
            { throw TicketNotFoundException("The specified ticket has not been found!") })
        ticketRepository.flush()
    }

    override fun getMessages(ticketId: Long, idExpert: Long): List<MessageDTO> {
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
        return employeeRepository.findByIdOrNull(idExpert)!!.listOfMessages.stream()
            .filter { it.ticket == ticket }.sorted().map {
                it.toDTO()
            }.toList()
    }

    override fun getStatus(ticketId: Long, idExpert: Long): TicketStatus {
        return ticketRepository.findById(ticketId)
            .orElseThrow { TicketNotFoundException("The specified ticket has not been found!") }
            .history.stream().sorted().findFirst().get().state
    }

    override fun addTicket(ticket: TicketDTO, idCustomer: Long) {
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

    override fun addMessage(idTicket: Long, message: MessageDTO) {
        var expert: Employee? = null
        ticketRepository.findById(idTicket)
            .ifPresentOrElse(
                {
                    if (historyRepository.findByTicketIdOrderByDateDesc(idTicket)
                            .first().state == TicketStatus.IN_PROGRESS
                    ) {
                        if (message.expert != null) {
                            //It is an expert
                            //TODO: Throw exception if history is empty
                            val lastHistory = historyRepository.findByTicketIdOrderByDateDesc(it.getId()!!)
                            if (lastHistory.isEmpty()) throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")

                            //TODO: Expert missmatch if history is not associated to the expert who replied
                            expert = employeeRepository.findByIdOrNull(lastHistory.first().employee?.getId()!!)
                                ?: throw ExpertNotFoundException("The specified expert has not been found!")
                        }
                        it.addMessage(
                            Message().create(
                                message.body,
                                LocalDateTime.now(),
                                null,
                                it,
                                expert //if not expert it remains null otherwise it is set
                            )
                        )
                    } else
                        throw OperationNotPermittedException("The ticket is not in progress!")
                },
                { throw TicketNotFoundException("The specified ticket has not been found!") })
    }

    override fun resolveTicket(ticketId: Long) {
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(ticketId)
        if(lastTicketHistory.isEmpty()) throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")

        val expert = lastTicketHistory.first().employee
        if (lastTicketHistory.first().state != TicketStatus.CLOSED && lastTicketHistory.first().state != TicketStatus.RESOLVED) {
            ticketRepository.findById(ticketId).ifPresentOrElse(
                {
                    it.addHistory(
                        History().create(
                            TicketStatus.RESOLVED,
                            LocalDateTime.now(),
                            it,
                            expert //If null than the ticket is closed before than an expert is assigned
                        )
                    )
                },
                { throw TicketNotFoundException("The specified ticket has not been found!") })
        } else {
            throw OperationNotPermittedException("This operation is not permitted because the ticket is closed!")
        }
    }

    override fun reopenTicket(ticketId: Long) {
        //Check the last history state related to the ticketId
        val lastTicketHistory = historyRepository.findByTicketIdOrderByDateDesc(ticketId)
        if(lastTicketHistory.isEmpty()) throw HistoryNotFoundException("The history associated to the specified ticket has not been found!")
        println(lastTicketHistory.first().state)
        if (lastTicketHistory.first().state == TicketStatus.CLOSED || lastTicketHistory.first().state == TicketStatus.RESOLVED) {
            ticketRepository.findById(ticketId).ifPresentOrElse(
                {
                    it.addHistory(
                        History().create(
                            TicketStatus.REOPENED,
                            LocalDateTime.now(),
                            it,
                            null // reopen so the ticket can be assigned to another expert
                        )
                    )
                },
                { throw TicketNotFoundException("The specified ticket has not been found!") })
        } else {
            throw OperationNotPermittedException("This operation is not permitted for the current ticket's state!")
        }
    }
}