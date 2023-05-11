package it.polito.wa2.ticketing.attachment

import it.polito.wa2.ticketing.customer.BlankFieldsException
import it.polito.wa2.ticketing.customer.PasswordMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class AttachmentProblemDetailsHandlers {
    @ExceptionHandler(AttachmentNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAttachmentNotFoundException(e: AttachmentNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

}

class AttachmentNotFoundException(override val message:String?): RuntimeException()