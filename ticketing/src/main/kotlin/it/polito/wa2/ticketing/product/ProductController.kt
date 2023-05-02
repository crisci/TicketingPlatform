package it.polito.wa2.ticketing.product

import it.polito.wa2.ticketing.ticket.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val productService: ProductService) {

    @GetMapping("/products/{productId}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun findTicketsByProductId(@PathVariable productId: Long): ProductDTO? {
        return productService.getTickets(productId)
    }
}