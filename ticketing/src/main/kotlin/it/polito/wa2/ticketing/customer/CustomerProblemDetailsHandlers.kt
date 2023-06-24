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

    @ExceptionHandler(DuplicatedEmailException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDuplicatedEmail(e: DuplicatedEmailException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

    @ExceptionHandler(InvalidEmailFormatException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidEmailFormat(e: InvalidEmailFormatException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

    @ExceptionHandler(BlankFieldsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBlankFields(e: BlankFieldsException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

    @ExceptionHandler(PasswordMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePasswordMismatch(e: PasswordMismatchException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

    @ExceptionHandler(PasswordTooShortException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePasswordTooShort(e: PasswordTooShortException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

    @ExceptionHandler(LoginErrorException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleLoginError(e: LoginErrorException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

}

class CustomerNotFoundException(override val message:String?) : RuntimeException()
class DuplicatedEmailException(override val message: String?) : RuntimeException()
class InvalidEmailFormatException(override val message: String?) : RuntimeException()
class BlankFieldsException(override val message: String?) : RuntimeException()
class PasswordMismatchException(override val message: String?) : RuntimeException()
class PasswordTooShortException(override val message: String?) : RuntimeException()
class LoginErrorException(override val message: String?) : RuntimeException()