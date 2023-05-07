package it.polito.wa2.ticketing.product

import it.polito.wa2.ticketing.ticket.TicketDTO

interface ProductService  {
    fun getTickets(productId: Long): ProductDTO?
    fun getAllProducts() : List<ProductDTO>
    fun getProduct(ean:String): ProductDTO?
}