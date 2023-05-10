package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service @Transactional
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val ticketRepository: TicketRepository
): MessageService {

    override fun getMessageAttachments(messageId: Long): Set<AttachmentDTO>? {
        return messageRepository.findById(messageId).orElseThrow{ MessageNotFoundException("Message not found with specified id") }.toMessageWithAttachmentsDTO().listOfAttachment
    }

    override fun addAttachments(messageId: Long, attachments: Set<AttachmentDTO>) {
        messageRepository.findById(messageId).ifPresentOrElse(
            { m ->
                attachments.forEach { a ->
                    m.addAttachment(Attachment().create(a.attachment, m))
                }
            },
            {
                throw MessageNotFoundException("Message not found with specified id")
            }
        )
    }

    override fun editMessage(messageId: Long, message: String) {
        messageRepository.findById(messageId).ifPresentOrElse(
            {
                val mes = it
                mes.body = message
                messageRepository.save(mes)
            },
            {
                throw MessageNotFoundException("Message not found with specified id")
            }
        )
    }

    override fun getMessagesByIdTickets(idTicket: Long): List<MessageDTO?> {
        //Check if the ticket exists
        if( ticketRepository.findById(idTicket).isPresent)
            return ticketRepository.findById(idTicket).get().listOfMessage.map { it.toDTO() }
        else
            throw TicketNotFoundException("The specified ticket has not been found!")

    }

}