package it.polito.wa2.server.products

import it.polito.wa2.server.product.ProductDTO
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product {
    @Id
    var ean: String = ""
    var name: String = ""
    var brand: String = ""
}


fun Product.fromDTO(productDTO: ProductDTO) : Product {
    val productFromDTO : Product = Product()
    productFromDTO.ean = productDTO.ean
    productFromDTO.name = productDTO.name
    productFromDTO.brand = productDTO.brand
    return productFromDTO
}