package it.polito.wa2.ticketing.customer

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomerProblemDetailHandlers: ResponseEntityExceptionHandler() {
    @ExceptionHandler(CustomerNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCustomerNotFound(e: CustomerNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

}

class CustomerNotFoundException(override val message:String?) : RuntimeException()