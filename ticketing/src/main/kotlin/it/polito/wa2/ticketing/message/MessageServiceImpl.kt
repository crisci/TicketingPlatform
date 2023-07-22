package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.*
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import it.polito.wa2.ticketing.utils.ImageUtil
import jakarta.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service @Transactional
class MessageServiceImpl(
    private val repository: MessageRepository,
    private val ticketRepository: TicketRepository,
    private val attachmentRepository: AttachmentRepository
): MessageService {



    @Secured("ROLE_Expert", "ROLE_Client")
    override fun editMessage(messageId: Long, message: String) {
        repository.findById(messageId).ifPresentOrElse(
            {
                val mes = it
                mes.body = message
                repository.save(mes)
            },
            {
                throw MessageNotFoundException("Message not found with specified id")
            }
        )
    }

    @Secured("ROLE_Expert", "ROLE_Client", "ROLE_Manager")
    override fun getMessagesByIdTickets(idTicket: Long): List<MessageDTO?> {
        //Check if the ticket exists
        if( ticketRepository.findById(idTicket).isPresent)
            return ticketRepository.findById(idTicket).get().listOfMessage.map { it.toDTO() }
        else
            throw TicketNotFoundException("The specified ticket has not been found!")

    }



}