package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.utils.ImageUtil
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import jakarta.transaction.Transactional
import org.apache.coyote.http11.Constants.a
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service @Transactional
class MessageServiceImpl(
    private val messageRepository: MessageRepository,
    private val ticketRepository: TicketRepository
): MessageService {
    override fun getMessageAttachments(messageId: Long): Set<ByteArray> {
        val img = messageRepository.findById(messageId)
            .orElseThrow{ MessageNotFoundException("Message not found with specified id") }
            .toMessageWithAttachmentsDTO().listOfAttachment
        val set = mutableSetOf<ByteArray>()
        img!!.forEach{
            set.add(ImageUtil().decompressImage(it.attachment!!)!!)
        }
        return set
    }

    override fun addAttachment(messageId: Long, attachment: Array<MultipartFile>) {
        messageRepository.findById(messageId).ifPresentOrElse(
            { m ->
                attachment.forEach { a ->
                    if (a.contentType == "image/png" || a.contentType == "image/jpeg") {
                        m.addAttachment(Attachment().create(ImageUtil().compressImage(a.bytes), m))
                    } else {
                        throw FileTypeNotSupportedException("Cannot send this type of file")
                    }
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