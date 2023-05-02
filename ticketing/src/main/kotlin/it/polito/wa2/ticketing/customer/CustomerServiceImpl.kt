package it.polito.wa2.ticketing.Cusotmer

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service @Transactional
class CustomerServiceImpl(private val repository: CustomerRepository): CustomerService {
     override fun getCustomer(id: Long): CustomerDTO? {
        return repository.findById(id).orElse(null)?.toDTO()
     }
}