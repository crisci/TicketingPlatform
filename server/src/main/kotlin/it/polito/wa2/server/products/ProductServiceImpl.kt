package it.polito.wa2.server.products

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service @Transactional
class ProductServiceImpl(private val repo: ProductRepository): ProductService {
    override fun getAllProducts(): List<ProductDTO> {
        return repo.findAll().map {p -> p.toDTO()}
    }

    override fun getProduct(productId: String): ProductDTO? {
        return repo.findByIdOrNull(productId)?.toDTO()
    }
    
}