package it.polito.wa2.ticketing

import it.polito.wa2.ticketing.attachment.Attachment
import it.polito.wa2.ticketing.attachment.AttachmentRepository
import it.polito.wa2.ticketing.customer.Customer
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeRepository
import it.polito.wa2.ticketing.history.History
import it.polito.wa2.ticketing.history.HistoryRepository
import it.polito.wa2.ticketing.message.Message
import it.polito.wa2.ticketing.message.MessageRepository
import it.polito.wa2.ticketing.product.Product
import it.polito.wa2.ticketing.product.ProductRepository
import it.polito.wa2.ticketing.ticket.Ticket
import it.polito.wa2.ticketing.ticket.TicketRepository
import it.polito.wa2.ticketing.utils.EmployeeRole
import it.polito.wa2.ticketing.utils.PriorityLevel
import it.polito.wa2.ticketing.utils.SenderType
import it.polito.wa2.ticketing.utils.TicketStatus
import org.junit.jupiter.api.Test
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
import java.sql.Blob
import java.time.LocalDate

@Testcontainers
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class TicketingApplicationTests {
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

	var customer: Customer = Customer()
	var product: Product = Product()
	var ticket: Ticket = Ticket()
	var history1: History = History()
	var history2: History = History()
	var expert: Employee = Employee()
	var admin: Employee = Employee()
	var message1: Message = Message()
	var message2: Message = Message()
	//var attachment: Attachment = Attachment()

	fun initialization() {
		customer.first_name = "Pietro"
		customer.last_name = "Bertorelle"
		customer.email = "email@gmail.com"
		customer.dob = LocalDate.of(1998,9,13)
		customer.password = "password"
		customer.address = "Via Rivalta"
		customer.phone_number = "3466088800"
		customerRepository.save(customer)

		product.ean = "4935531465706"
		product.name = "JMT X-ring 530x2 Gold 104 Open Chain With Rivet Link for Kawasaki KH 400 a 1976"
		product.brand = "JMT"
		productRepository.save(product)

		expert.first_name = "Francesca"
		expert.last_name = "Ferritti"
		expert.email = "myemail@gmail.com"
		expert.password = "password"
		employeeRepository.save(expert)

		admin.first_name = "Giulio"
		admin.last_name = "Rossetti"
		admin.email = "myemail2@gmail.com"
		admin.password = "password"
		admin.type = EmployeeRole.ADMIN
		employeeRepository.save(admin)

		ticket.title = "Can't use the product"
		ticket.description = "How should i assemble the product?"
		ticket.priority = PriorityLevel.MEDIUM
		ticketRepository.save(ticket)

		message1.type = SenderType.EXPERT
		message1.body = "Try sending a picture"
		messageRepository.save(message1)

		message2.type = SenderType.CUSTOMER
		message2.body = "The picture is in the attachment!"
		messageRepository.save(message2)

		history2.state = TicketStatus.IN_PROGRESS
		historyRepository.save(history1)
		historyRepository.save(history2)

		//attachment.attachment = null
		//attachmentRepository.save(attachment)

		//the customer open the ticket
		customer.addTicket(ticket)
		//on a product
		product.addTicket(ticket)
		//the admin get the ticket as open
		admin.addHistory(history1)
		ticket.addHistory(history1)
		//the admin assign the ticket to the expert
		expert.addHistory(history2)
		ticket.addHistory(history2)
		//the expert send a message
		expert.addMessage(message1)
		ticket.addMessage(message1)
		//the customer prepare the message
		//message2.addAttachment(attachment)
		//the customer send the message
		expert.addMessage(message2)
		ticket.addMessage(message2)

		//save all
		customerRepository.save(customer)
		productRepository.save(product)
		ticketRepository.save(ticket)
		historyRepository.save(history1)
		historyRepository.save(history2)
		employeeRepository.save(expert)
		employeeRepository.save(admin)
		messageRepository.save(message1)
		messageRepository.save(message2)
		//attachmentRepository.save(attachment)
		//flush all
		customerRepository.flush()
		productRepository.flush()
		ticketRepository.flush()
		historyRepository.flush()
		employeeRepository.flush()
		messageRepository.flush()
		//attachmentRepository.flush()
	}
	@Test
	fun integrationTest(){
		initialization()
	}

}
