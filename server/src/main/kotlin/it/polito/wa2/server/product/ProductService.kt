package it.polito.wa2.server.product

interface ProductService {
    fun getAllProducts() : List<ProductDTO>
    fun getProduct(productId:String):ProductDTO?
}
