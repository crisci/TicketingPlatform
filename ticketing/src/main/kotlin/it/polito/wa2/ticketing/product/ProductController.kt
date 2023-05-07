package it.polito.wa2.ticketing.product

import it.polito.wa2.ticketing.ticket.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val productService: ProductService) {

    @GetMapping("/API/products/{productId}/tickets/")
    @ResponseStatus(HttpStatus.OK)
    fun findTicketsByProductId(@PathVariable productId: Long): List<TicketDTO>? {
        return productService.getTickets(productId)
    }

    @GetMapping("/API/products/")
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts() : List<ProductDTO> {
        return productService.getAllProducts()
    }
    @GetMapping("/API/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getProductById(@PathVariable("productId") productId:String) : ProductDTO {
        if(productService.getProduct(productId)!=null)
            return productService.getProduct(productId)!!
        else
            throw ProductNotFoundException("No element found with specified id!")
    }


}