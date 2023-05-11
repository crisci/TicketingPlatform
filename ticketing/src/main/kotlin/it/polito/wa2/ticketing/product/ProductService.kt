package it.polito.wa2.ticketing.product

import it.polito.wa2.ticketing.ticket.TicketDTO

interface ProductService  {
    fun getTickets(productId: Long): List<TicketDTO>?
    fun getAllProducts() : List<ProductDTO>
    fun getProduct(ean:String): ProductDTO?
    fun addProduct(product: ProductDTO)
    fun updateProduct(productId: String, product: ProductDTO)
    fun deleteProduct(productId: String)

}