package it.polito.wa2.ticketing.signup

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SignupProblemDetailsHandler {

    @ExceptionHandler(SignupError::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleSignupError(e: SignupError) = ProblemDetail
        .forStatusAndDetail( HttpStatus.CONFLICT, e.message!! )

    @ExceptionHandler(TokenRefreshErrorException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleTokenRefreshError(e: TokenRefreshErrorException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )



}

class SignupError(override val message:String?) : RuntimeException()
class TokenRefreshErrorException(override val message:String?) : RuntimeException()