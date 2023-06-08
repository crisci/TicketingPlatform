package it.polito.wa2.ticketing.signup

import it.polito.wa2.ticketing.customer.Customer
import it.polito.wa2.ticketing.customer.CustomerDTO
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
import java.time.LocalDate
import java.util.*

@Service
class SignupServiceImpl(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository
): SignupService {

    private val authUrl = "http://localhost:8080/"
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
                    Customer().create(
                        UUID.fromString(userId),
                        credentials["firstName"]!!,
                        credentials["lastName"]!!,
                        credentials["email"]!!,
                        LocalDate.parse(credentials["dob"]!!),
                        credentials["address"]?:"",
                        credentials["phoneNumber"]!!,
                    )
                )

            } catch (e: Exception) {
                keycloak.realm("ticketing").users().delete(userId)
                customerRepository.deleteById(UUID.fromString(userId))
                throw SignupError("Error creating user")
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
                Employee().createExpert(
                    UUID.fromString(userId),
                    credentials["firstName"]!!,
                    credentials["lastName"]!!,
                    credentials["email"]!!
                )
            )

        } catch (e: Exception) {
            keycloak.realm("ticketing").users().delete(userId)
            employeeRepository.deleteById(UUID.fromString(userId))
            throw SignupError("Error creating user")
        } finally {
            keycloak.close()
        }
    }
}