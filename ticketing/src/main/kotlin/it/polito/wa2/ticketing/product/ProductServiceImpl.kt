package it.polito.wa2.ticketing.product


import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service @Transactional
class ProductServiceImpl(private val repository: ProductRepository) : ProductService {
    override fun getTickets(productId: Long): ProductDTO? {
        return repository.findProductByEan(productId.toString())?.toDTO()
    }
}