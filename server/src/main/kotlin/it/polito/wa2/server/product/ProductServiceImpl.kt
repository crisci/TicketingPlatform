package it.polito.wa2.server.product

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service @Transactional
class ProductServiceImpl(private val repo: ProductRepository):ProductService {
    override fun getAllProducts(): List<ProductDTO> {
        return repo.findAll().map(){p -> p.toDTO()}
    }

    override fun getProduct(productId: String): ProductDTO? {
        return repo.findByIdOrNull(productId)?.toDTO()
    }

    override fun putProduct(productDTO: ProductDTO) {
        if(repo.existsById(productDTO.ean)){
            repo.saveAndFlush(Product(productDTO.ean,productDTO.name,productDTO.brand))
        }
        else throw ProductNotFoundException("Can't find a product with the specified EAN")
    }
}