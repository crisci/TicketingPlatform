package it.polito.wa2.ticketing.product

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ProductProblemDetailHandlers: ResponseEntityExceptionHandler() {
    @ExceptionHandler(ProductNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProductNotFound(e: ProductNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

    @ExceptionHandler(BlankFieldsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBlankFields(e: BlankFieldsException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.BAD_REQUEST, e.message!! )

    @ExceptionHandler(ProductAlreadyRegisteredException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleProductAlreadyRegistered(e: ProductAlreadyRegisteredException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.CONFLICT, e.message!! )

    @ExceptionHandler(ProductNotRegistered::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProductNotRegistered(e: ProductNotRegistered) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message!! )

    @ExceptionHandler(ProductAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleProductAlreadyExists(e: ProductAlreadyExistsException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.CONFLICT, e.message!! )

}

class ProductNotFoundException(override val message:String?) : RuntimeException()
class BlankFieldsException(override val message: String?) : RuntimeException()
class ProductAlreadyRegisteredException(override val message: String?) : RuntimeException()
class ProductNotRegistered(override val message: String?) : RuntimeException()
class ProductAlreadyExistsException(override val message: String?) : RuntimeException()