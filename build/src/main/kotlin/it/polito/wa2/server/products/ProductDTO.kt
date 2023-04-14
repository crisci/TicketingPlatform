package it.polito.wa2.server.product

import it.polito.wa2.server.products.Product

data class ProductDTO(
    val ean: String,
    val name:String,
    val brand: String
)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean,name,brand)
}