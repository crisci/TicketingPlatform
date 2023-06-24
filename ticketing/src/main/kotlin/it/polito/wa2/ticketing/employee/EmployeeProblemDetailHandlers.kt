package it.polito.wa2.ticketing.employee

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class EmployeeProblemDetailHandlers: ResponseEntityExceptionHandler() {
    @ExceptionHandler(ExpertNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleExpertNotFound(e: ExpertNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

}

class ExpertNotFoundException(override val message:String?) : RuntimeException()