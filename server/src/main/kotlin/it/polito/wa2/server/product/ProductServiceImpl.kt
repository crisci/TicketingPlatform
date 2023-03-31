package it.polito.wa2.server.product

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val repo: ProductRepository):ProductService {
    override fun getAllProducts(): List<ProductDTO> {
        return repo.findAll().map(){p -> p.toDTO()}
    }

    override fun getProduct(productId: String): ProductDTO? {
        return repo.findByIdOrNull(productId)?.toDTO()
    }
}