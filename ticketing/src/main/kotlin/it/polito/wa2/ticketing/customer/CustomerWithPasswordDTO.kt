package it.polito.wa2.ticketing.customer

data class CustomerWithPasswordDTO(
    val customer: CustomerDTO,
    val password: String
)

fun Customer.toCustomerWithPasswordDTO(): CustomerWithPasswordDTO {
    return CustomerWithPasswordDTO( toDTO(), password )
}
