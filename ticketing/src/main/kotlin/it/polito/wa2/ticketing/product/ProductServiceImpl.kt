package it.polito.wa2.ticketing.product


import it.polito.wa2.ticketing.ticket.TicketDTO
import it.polito.wa2.ticketing.ticket.toTicketDTO
import jakarta.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service @Transactional
class ProductServiceImpl(private val repository: ProductRepository) : ProductService {


    @Secured("ROLE_Manager")
    override fun getTickets(productId: Long): List<TicketDTO>? {
        return repository.findProductByEan(productId.toString())?.listOfTicket?.map { it.toTicketDTO() } ?: throw ProductNotFoundException("No product found with specified id!")
    }

    @Secured("ROLE_Manager")
    override fun getAllProducts(): List<ProductDTO> {
        return repository.findAll().map {p -> p.toDTO()}
    }

    @Secured("ROLE_Manager")
    override fun getProduct(ean: String): ProductDTO? {
        return repository.findProductByEan(ean)?.toDTO() ?: throw ProductNotFoundException("No product found with specified id!")
    }

    @Secured("ROLE_Manager")
    override fun addProduct(product: ProductDTO) {
        repository.save(Product().create(product.ean, product.name, product.brand))
    }

    @Secured("ROLE_Manager")
    override fun updateProduct(productId: String, product: ProductDTO) {
        if(productId != "" && product.name != "" && product.brand != "" && product.ean != ""){
            val p = repository.findProductByEan(productId)
            if(p != null){
                p.ean = product.ean
                p.name = product.name
                p.brand = product.brand
                repository.save(p)
            }else{
                throw ProductNotFoundException("No product found with specified id!")
            }
        }else{
            throw BlankFieldsException("All product fields must be filled")
        }
    }

    @Secured("ROLE_Manager")
    override fun deleteProduct(productId: String) {
        val p = repository.findProductByEan(productId)
        if(p != null){
            repository.delete(p)
        }else{
            throw ProductNotFoundException("No product found with specified id!")
        }
    }


}
