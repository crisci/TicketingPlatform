package it.polito.wa2.ticketing.product

import io.micrometer.observation.annotation.Observed
import it.polito.wa2.ticketing.ticket.TicketDTO
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Observed
class ProductController(private val productService: ProductService) {


    @GetMapping("/API/products")
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts() : List<ProductDTO> {
        return productService.getAllProducts()
    }
    @PostMapping("/API/products")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@RequestBody product: ProductDTO){
        if( product.ean.isNotBlank() && product.name.isNotBlank() && product.brand.isNotBlank() )
        {
            productService.addProduct(product)
        } else {
            throw BlankFieldsException("Fields cannot be black")
        }
    }
    @GetMapping("/API/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getProductById(@PathVariable productId:String) : ProductDTO {
        if(productService.getProduct(productId)!=null)
            return productService.getProduct(productId)!!
        else
            throw ProductNotFoundException("No element found with specified id!")
    }
    @PutMapping("/API/products/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateProduct(@PathVariable productId: String, @RequestBody product: ProductDTO){
        if( product.ean.isNotBlank() && product.name.isNotBlank() && product.brand.isNotBlank() )
        {
            if(productService.getProduct(productId) != null){
                productService.updateProduct(productId, product)
            }else{
                throw ProductNotFoundException("No element found with specified id!")
            }
        }else{
            throw BlankFieldsException("Fields cannot be black")
        }
    }
    @DeleteMapping("/API/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable productId: String){
        if(productService.getProduct(productId) != null){
            productService.deleteProduct(productId)
        }else{
            throw ProductNotFoundException("No element found with specified id!")
        }
    }
    @GetMapping("/API/products/{productId}/tickets")
    @ResponseStatus(HttpStatus.OK)
    fun findTicketsByProductId(@PathVariable productId: Long): List<TicketDTO>? {
        return productService.getTickets(productId)
    }
}