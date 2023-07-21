package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.history.OperationNotPermittedException
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.message.MessageDTO
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import it.polito.wa2.ticketing.ticket.toTicketDTO
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class ExpertServiceImpl(private val ticketRepository: TicketRepository,
                        private val historyRepository: HistoryRepository,
                        private val employeeRepository: EmployeeRepository
):ExpertService {

    @Secured("ROLE_Expert")
    override fun getTickets(idExpert: UUID): List<TicketDTO?> {
        return ticketRepository.findTicketByMostRecentExpert(idExpert).stream().map {it?.toTicketDTO()}.toList()
    }

    @Secured("ROLE_Manager")
    override fun reassignTicket(ticketId: Long, idExpert: UUID) {
        val ticket: Ticket? = ticketRepository.findById(ticketId).get();
        if(ticket == null){
            throw TicketNotFoundException("The specified ticket has not been found!")
        }
        val expert: Employee? = employeeRepository.findById(idExpert).get();
        if(expert == null){
            throw ExpertNotFoundException("can't find the specified expert")
        }
        ticket.addHistory(History().create(TicketStatus.IN_PROGRESS, LocalDateTime.now(), ticket, expert));
        ticketRepository.save(ticket); 
        ticketRepository.flush()
    }

    @Secured("ROLE_Expert")
    override fun addMessage(idTicket: Long, message: MessageDTO, expertId: UUID) {
        ticketRepository.findById(idTicket)
            .ifPresentOrElse(
                {
                    val histories = historyRepository.findByTicketIdOrderByDateDesc(idTicket)
                    if (histories.isNotEmpty() && histories
                            .first()?.state == TicketStatus.IN_PROGRESS
                    ) {
                        val employee = employeeRepository.findById(expertId)
                        val newMessage = Message().create(
                            message.body,
                            LocalDateTime.now(),
                            mutableSetOf(), //Mutable set of attachments
                            it,
                            employee.get() //if not expert it remains null otherwise it is set
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

}