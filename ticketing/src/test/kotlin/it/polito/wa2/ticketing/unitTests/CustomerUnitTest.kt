package it.polito.wa2.ticketing.unitTests

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentRepository
import it.polito.wa2.ticketing.customer.Customer
import it.polito.wa2.ticketing.customer.CustomerNotFoundException
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.customer.InvalidEmailFormatException
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeRepository
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.message.*
import it.polito.wa2.ticketing.product.Product
import it.polito.wa2.ticketing.product.ProductRepository
import it.polito.wa2.ticketing.ticket.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class CustomerUnitTest {
    companion object {
        @Container
        val postgres = PostgreSQLContainer("postgres:latest")
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.jpa.hibernate.ddl-auto") {"create-drop"}
        }
    }
    @LocalServerPort
    protected var port: Int = 8081
    @Autowired
    lateinit var restTemplate: TestRestTemplate
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var ticketRepository: TicketRepository
    @Autowired
    lateinit var historyRepository: HistoryRepository
    @Autowired
    lateinit var employeeRepository: EmployeeRepository
    @Autowired
    lateinit var messageRepository: MessageRepository
    @Autowired
    lateinit var attachmentRepository: AttachmentRepository
    @Autowired
    lateinit var ticketService: TicketService
    @Autowired
    lateinit var messageService: MessageService

    var customer: Customer = Customer()
    var product: Product = Product()
    var ticket: Ticket = Ticket()
    var history1: History = History()
    var history2: History = History()
    var expert: Employee = Employee()
    var admin: Employee = Employee()
    var message1: Message = Message()
    var message2: Message = Message()
    var attachment: Attachment = Attachment()

    @Nested
    @DisplayName("GET /API/customers/id={idCustomer}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCustomerById() {

        @Test
        fun customerNotFound() {
            val idCustomer = -1
            val response = restTemplate.getForEntity("/API/customers/id=$idCustomer", CustomerNotFoundException::class.java)
            assert(response.statusCode.is4xxClientError)
        }

        @Test
        fun returnCustomer() {
            val customer = Customer().apply {
                first_name = "Pietro"
                last_name = "Bertorelle"
                email = "pit@polito.it"
                dob = LocalDate.of(1998,9,13)
                address = "Address"
            }
            customerRepository.save(customer)

            val response = restTemplate.getForEntity("/API/customers/id=${customer.getId()}", Customer::class.java)
            assert(response.body == customer)

            customerRepository.deleteAll()
        }

    }

    @Nested
    @DisplayName("GET /API/customers/email={email}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCustomerByEmail() {

        @Test
        fun customerNotFound() {
            val idCustomer = -1
            val response = restTemplate.getForEntity("/API/customers/id=$idCustomer", CustomerNotFoundException::class.java)
            assert(response.statusCode.is4xxClientError)
        }

        @Test
        fun invalidEmail() {
            val customer = Customer().apply {
                first_name = "Pietro"
                last_name = "Bertorelle"
                email = "pitpolito.it"
                dob = LocalDate.of(1998,9,13)
                address = "Address"
            }
            customerRepository.save(customer)

            val response = restTemplate.getForEntity("/API/customers/email=${customer.email}", InvalidEmailFormatException::class.java)
            assert(response.statusCode.is4xxClientError)

            customerRepository.deleteAll()
        }

        @Test
        fun returnCustomer() {
            val customer = Customer().apply {
                first_name = "Pietro"
                last_name = "Bertorelle"
                email = "pit@polito.it"
                dob = LocalDate.of(1998,9,13)
                address = "Address"
            }
            customerRepository.save(customer)

            val response = restTemplate.getForEntity("/API/customers/email=${customer.email}", Customer::class.java)
            assert(response.body == customer)
        }

    }

}