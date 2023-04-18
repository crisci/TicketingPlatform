package it.polito.wa2.server.products

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product,String> {
//Jpa is the base class of all DAO(Repository in kotlin), String is type of primary key
}