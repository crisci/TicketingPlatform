package it.polito.wa2.server.profiles

class ProfileDTO(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String
)

fun Profile.toDTO() : ProfileDTO {
    return ProfileDTO(id, name, surname, email)
}

