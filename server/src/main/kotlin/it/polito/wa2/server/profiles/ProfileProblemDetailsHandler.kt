package it.polito.wa2.server.profiles

import org.springframework.dao.DuplicateKeyException
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI

@RestControllerAdvice
class ProfileProblemDetailsHandler : ResponseEntityExceptionHandler() {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProfileNotFoundException::class)
    fun handleProfileNotFound(e: ProfileNotFoundException) : ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "Profile not found"
        d.detail = e.message
        //d.type = URI.create("https://apit.myservice.com/errors/profile-not-found")
        return d
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedEmailException::class)
    fun handleDuplicateEmailException(e: DuplicatedEmailException) : ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "The same email is already used"
        d.detail = e.message
        return d
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailFormatException::class)
    fun handleInvelidEmailFormatException(e: InvalidEmailFormatException) : ProblemDetail {
        val d = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        d.title = "The email value provided is not a valid email format"
        d.detail = e.message
        return d
    }

}

class ProfileNotFoundException(override val message: String?) : RuntimeException()
class DuplicatedEmailException(override val message: String?) : RuntimeException()
class InvalidEmailFormatException(override val message: String?) : RuntimeException()
