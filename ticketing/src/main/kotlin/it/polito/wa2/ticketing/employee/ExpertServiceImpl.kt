package it.polito.wa2.ticketing.employee

import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.history.OperationNotPermittedException
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import it.polito.wa2.ticketing.ticket.toTicketDTO
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.TicketStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.streams.toList

@Service
@Transactional
class ExpertServiceImpl(private val ticketRepository: TicketRepository,
                        private val historyRepository: HistoryRepository
):ExpertService {

    override fun getTickets(idExpert: UUID): List<TicketDTO> {
        return ticketRepository.findTicketByMostRecentExpert(idExpert).stream().map {it.toTicketDTO()}.toList()
    }

    override fun reassignTicket(ticketId: Long, idExpert: UUID) {
        ticketRepository.findById(ticketId).ifPresentOrElse(
            {
                if (historyRepository.findByTicketIdOrderByDateDesc(ticketId).first().state != TicketStatus.OPEN) {

                    var admin: Employee? = null
                    for (h: History in it.history.sorted()) {
                        if (h.employee == null || h.employee?.type == EmployeeRole.MANAGER) {
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

}