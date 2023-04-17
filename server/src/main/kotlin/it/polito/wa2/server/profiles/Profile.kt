package it.polito.wa2.server.profiles

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table


@Entity
@Table(name="profiles")
class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "id", sequenceName = "users_id_seq")
    var id: Long = 0
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
}

fun Profile.fromDTO(profileDTO: ProfileDTO) : Profile {
    val profileFromDTO : Profile = Profile()
    profileFromDTO.name = profileDTO.name
    profileFromDTO.surname = profileDTO.surname
    profileFromDTO.email = profileDTO.email.lowercase()
    return profileFromDTO
}