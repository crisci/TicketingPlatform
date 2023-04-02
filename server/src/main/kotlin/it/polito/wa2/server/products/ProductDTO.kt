package it.polito.wa2.server.products

class ProductDTO(
    val ean: String,
    val name: String,
    val brand: String
)

//Extension method of the Product class
fun Product.toDTO() : ProductDTO {
    return  ProductDTO(ean, name, brand)
}
