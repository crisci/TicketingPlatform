package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service @Transactional
class MessageServiceImpl(
    private val repository: MessageRepository
): MessageService {

    override fun getMessageAttachments(messageId: Long): Set<AttachmentDTO>? {
        return repository.findById(messageId).orElseThrow{ MessageNotFoundException("Message not found with specified id") }.toMessageWithAttachmentsDTO().listOfAttachment
    }

    override fun addAttachments(messageId: Long, attachments: Set<AttachmentDTO>) {
        repository.findById(messageId).ifPresentOrElse(
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

}