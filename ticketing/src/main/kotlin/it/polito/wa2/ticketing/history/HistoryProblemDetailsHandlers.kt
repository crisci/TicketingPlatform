package it.polito.wa2.ticketing.history

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class HistoryProblemDetailHandlers: ResponseEntityExceptionHandler() {
    @ExceptionHandler(HistoryNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleHistoryNotFound(e: HistoryNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

    @ExceptionHandler(OperationNotPermittedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleOperationNotPermitted(e: OperationNotPermittedException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.FORBIDDEN, e.message!! )

}

class HistoryNotFoundException(override val message:String?) : RuntimeException()
class OperationNotPermittedException(override val message:String?) : RuntimeException()