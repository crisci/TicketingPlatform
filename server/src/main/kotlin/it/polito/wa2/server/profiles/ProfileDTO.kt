package it.polito.wa2.server.profiles

import jakarta.validation.constraints.Email

class ProfileDTO(
    val name: String,
    val surname: String,
    val email: String
)

fun Profile.toDTO() : ProfileDTO {
    return ProfileDTO(name, surname, email)
}

