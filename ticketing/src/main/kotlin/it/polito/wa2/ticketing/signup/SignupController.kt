package it.polito.wa2.ticketing.signup

import org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.representations.idm.ClientRepresentation
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class SignupController (private val signupService: SignupService)
{

    @PostMapping("/API/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(@RequestBody credentials: Map<String, String>) {
        signupService.signupCustomer(credentials)
    }
}