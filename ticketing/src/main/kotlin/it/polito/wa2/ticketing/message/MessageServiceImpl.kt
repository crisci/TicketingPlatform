package it.polito.wa2.ticketing.message

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentDTO
import it.polito.wa2.ticketing.utils.ImageUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service @Transactional
class MessageServiceImpl(
    private val repository: MessageRepository
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

}