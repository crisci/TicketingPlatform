package it.polito.wa2.ticketing.product


import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.toDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service @Transactional
class ProductServiceImpl(private val repository: ProductRepository) : ProductService {
    override fun getTickets(productId: Long): ProductDTO? {
        return repository.getProductByEan(productId.toString())?.toDTO()
    }
}