package it.polito.wa2.server.product

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(val productService: ProductService) {
    @GetMapping("/API/products")
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
    @PutMapping("/API/products/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun putProduct(@PathVariable("productId") productId:String,@RequestBody productDTO: ProductDTO) {
        if(productId == productDTO.ean)
            productService.putProduct(productDTO)
        else
            throw ProductNotFoundException("Product EANs mismatches!")
    }
}