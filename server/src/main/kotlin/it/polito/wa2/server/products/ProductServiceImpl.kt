package it.polito.wa2.server.products

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping

@Service //Responsible of business logic
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {

    override fun getAll(): List<ProductDTO> {
        return productRepository.findAll().map { it.toDTO() }
    }

    override fun getProduct(ean: String): ProductDTO? {
        return productRepository
            .findByIdOrNull(ean)
            ?.toDTO()
    }



}