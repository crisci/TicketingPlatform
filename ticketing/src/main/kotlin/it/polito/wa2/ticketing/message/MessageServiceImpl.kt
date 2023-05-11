package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.*
import it.polito.wa2.ticketing.ticket.TicketNotFoundException
import it.polito.wa2.ticketing.ticket.TicketRepository
import it.polito.wa2.ticketing.utils.ImageUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service @Transactional
class MessageServiceImpl(
    private val repository: MessageRepository,
    private val ticketRepository: TicketRepository,
    private val attachmentRepository: AttachmentRepository
): MessageService {

    override fun getMessageAttachments(messageId: Long): Set<AttachmentDTO> {
        val a = repository.findById(messageId)
            .orElseThrow{ MessageNotFoundException("Message not found with specified id") }
            .toMessageWithAttachmentsDTO().listOfAttachment
        val set = mutableSetOf<AttachmentDTO>()
        a!!.forEach{
            val b = AttachmentDTO(it.id, ImageUtil().decompressImage(it.attachment!!))
            set.add(b)
        }
        return set
    }

    override fun addAttachment(messageId: Long, attachment: Array<MultipartFile>) {
        repository.findById(messageId).ifPresentOrElse(
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

    override fun getMessagesByIdTickets(idTicket: Long): List<MessageDTO?> {
        //Check if the ticket exists
        if( ticketRepository.findById(idTicket).isPresent)
            return ticketRepository.findById(idTicket).get().listOfMessage.map { it.toDTO() }
        else
            throw TicketNotFoundException("The specified ticket has not been found!")

    }

    override fun getAttachment(idAttachment: Long): ByteArray {
        if (attachmentRepository.findById(idAttachment).isPresent) {
            val v = attachmentRepository.findById(idAttachment).get()
            return ImageUtil().decompressImage(v.attachment!!)!!
        } else {
            throw AttachmentNotFoundException("The specified attachment has not been found!")
        }
    }

}