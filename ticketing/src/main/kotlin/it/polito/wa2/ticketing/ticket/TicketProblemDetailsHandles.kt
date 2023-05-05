package it.polito.wa2.ticketing.ticket

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class TicketProblemDetailsHandles: ResponseEntityExceptionHandler() {
    @ExceptionHandler(TicketNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleTicketNotFound(e: TicketNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

    @ExceptionHandler(DuplicateTicketException::class)
    fun handleDuplicateTicket(e: DuplicateTicketException) = ProblemDetail
        .forStatusAndDetail(HttpStatus.CONFLICT, e.message!! )
}
    
class TicketNotFoundException(override val message:String?) : RuntimeException()
class DuplicateTicketException(override val message:String?) : RuntimeException()