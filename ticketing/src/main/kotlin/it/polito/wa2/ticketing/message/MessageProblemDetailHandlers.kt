package it.polito.wa2.ticketing.message

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class MessageProblemDetailHandlers: ResponseEntityExceptionHandler() {

    @ExceptionHandler(MessageNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleMessageNotFound(e: MessageNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

    @ExceptionHandler(FileTypeNotSupportedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleFileTypeNotSupported(e: FileTypeNotSupportedException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

}

class MessageNotFoundException(override val message:String?): RuntimeException()
class FileTypeNotSupportedException(override val message:String?): RuntimeException()