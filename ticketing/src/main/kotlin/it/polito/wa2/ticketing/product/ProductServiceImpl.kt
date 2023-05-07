package it.polito.wa2.ticketing.product


import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service @Transactional
class ProductServiceImpl(private val repository: ProductRepository) : ProductService {
    override fun getTickets(productId: Long): ProductDTO? {
        return repository.findProductByEan(productId.toString())?.toDTO()
    }

    override fun getAllProducts(): List<ProductDTO> {
        return repository.findAll().map {p -> p.toDTO()}
    }

    override fun getProduct(ean: String): ProductDTO? {
        return repository.findProductByEan(ean)?.toDTO()
    }
}