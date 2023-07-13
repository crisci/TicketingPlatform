package it.polito.wa2.ticketing.signup

import it.polito.wa2.ticketing.customer.Customer
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import it.polito.wa2.ticketing.customer.CustomerRepository
import it.polito.wa2.ticketing.employee.Employee
import it.polito.wa2.ticketing.employee.EmployeeRepository
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate
import java.util.*

@Service
class SignupServiceImpl(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository
): SignupService {

    private val authUrl = "http://keycloak:8080/"
    private val realm = "ticketing"

    private fun open(): Keycloak {
        return KeycloakBuilder.builder()
            .serverUrl(authUrl)
            .realm(realm)
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .clientId("admin-cli")
            .clientSecret("eca6Wae8SYXHJYSxxAeh5Gs38HZP3tPg")
            .build()
    }

    private fun customerValidation(customer: Customer): Customer {
        //all fields less than 55 chars and address less than 100 chars
        if (customer.first_name.length > 55 || customer.last_name.length > 55 || customer.address.length > 100) {
            throw SignupError("Invalid customer data")
        }
        //email validation
        if (!customer.email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) {
            throw SignupError("Invalid customer email")
        }
        //phone number validation
        if (!customer.phone_number.matches(Regex("[0-9]{10}"))) {
            throw SignupError("Invalid customer phone number")
        }
        //dob validation
        if (customer.dob != null && customer.dob!!.isAfter(LocalDate.now())) {
            throw SignupError("Invalid customer date of birth")
        }
        if(customerRepository.findByEmail(customer.email) != null)
            throw SignupError("Email already in use")
        return customer
    }

    private fun employeeValidation(employee: Employee): Employee {
        //all fields less than 55 chars and address less than 100 chars
        if (employee.first_name.length > 55 || employee.last_name.length > 55) {
            throw SignupError("Invalid employee data")
        }
        //email validation
        if (!employee.email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) {
            throw SignupError("Invalid employee data")
        }
        if (employeeRepository.findByEmail(employee.email).isPresent) {
            throw SignupError("Email already in use")
        }
        return employee
    }

    override fun signupCustomer(credentials: Map<String, String>) {

        val keycloak = open()
        var userId: String = UUID.randomUUID().toString()

            try {
                val userRepresentation = UserRepresentation()
                userRepresentation.isEnabled = true
                userRepresentation.username = credentials["username"]
                userRepresentation.firstName = credentials["firstName"]
                userRepresentation.lastName = credentials["lastName"]
                userRepresentation.email = credentials["email"]
                userRepresentation.isEmailVerified = true

                val passwordCred = CredentialRepresentation()
                passwordCred.isTemporary = false
                passwordCred.type = CredentialRepresentation.PASSWORD
                passwordCred.value = credentials["password"]
                userRepresentation.credentials = listOf(passwordCred)

                val response = keycloak.realm("ticketing").users().create(userRepresentation)
                if (response.status != 201) {
                    throw SignupError("Error creating user")
                }

                //ROLE ASSIGNMENT
                userId = CreatedResponseUtil.getCreatedId(response)
                val roleRepresentation = keycloak.realm("ticketing").roles().get("app_client").toRepresentation()
                keycloak.realm("ticketing").users().get(userId).roles().realmLevel()
                    .add(mutableListOf(roleRepresentation))

                //DB Insertion
                customerRepository.save(
                   customerValidation( Customer().create(
                       UUID.fromString(userId),
                       credentials["firstName"]!!,
                       credentials["lastName"]!!,
                       credentials["email"]!!,
                       LocalDate.parse(credentials["dob"]!!),
                       credentials["address"]?:"",
                       credentials["phoneNumber"]!!,
                   ))
                )

            } catch (e: Exception) {
                keycloak.realm("ticketing").users().delete(userId)
                customerRepository.deleteById(UUID.fromString(userId))
                throw SignupError(e.message)
            } finally {
                keycloak.close()
            }
    }

    override fun createExpert(credentials: Map<String, String>) {
        val keycloak = open()
        var userId: String = UUID.randomUUID().toString()

        try {
            val userRepresentation = UserRepresentation()
            userRepresentation.isEnabled = true
            userRepresentation.username = credentials["username"]
            userRepresentation.firstName = credentials["firstName"]
            userRepresentation.lastName = credentials["lastName"]
            userRepresentation.email = credentials["email"]
            userRepresentation.isEmailVerified = true

            val passwordCred = CredentialRepresentation()
            passwordCred.isTemporary = false
            passwordCred.type = CredentialRepresentation.PASSWORD
            passwordCred.value = credentials["password"]
            userRepresentation.credentials = listOf(passwordCred)

            val response = keycloak.realm("ticketing").users().create(userRepresentation)
            if (response.status != 201) {
                throw SignupError("Error creating user")
            }

            //ROLE ASSIGNMENT
            userId = CreatedResponseUtil.getCreatedId(response)
            val roleRepresentation = keycloak.realm("ticketing").roles().get("app_expert").toRepresentation()
            keycloak.realm("ticketing").users().get(userId).roles().realmLevel()
                .add(mutableListOf(roleRepresentation))

            //DB Insertion
            employeeRepository.save(
                employeeValidation(Employee().createExpert(
                    UUID.fromString(userId),
                    credentials["firstName"]!!,
                    credentials["lastName"]!!,
                    credentials["email"]!!
                ))
            )

        } catch (e: Exception) {
            keycloak.realm("ticketing").users().delete(userId)
            employeeRepository.deleteById(UUID.fromString(userId))
            throw SignupError(e.message)
        } finally {
            keycloak.close()
        }
    }

    override fun refresh(credentials: Map<String, String>): ResponseEntity<String> {
        val restTemplate = RestTemplate()

        val url = "http://keycloak:8080/realms/ticketing/protocol/openid-connect/token"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val requestBody: MultiValueMap<String, String> = LinkedMultiValueMap()
        requestBody.add("grant_type", "refresh_token")
        requestBody.add("client_id", "authN")
        requestBody.add("refresh_token", credentials["refresh_token"])

        val uri = UriComponentsBuilder.fromHttpUrl(url).build().encode().toUri()

        try {
            val requestEntity = HttpEntity(requestBody, headers)
            return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String::class.java
            )
        } catch (e: Exception) {
            throw TokenRefreshErrorException("Error during token refresh")
        }
    }
}
