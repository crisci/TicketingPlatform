package it.polito.wa2.server.profiles

import jakarta.persistence.*


@Entity
@Table(name="profiles")
class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "id", sequenceName = "users_id_seq")
    var id: Int = 0
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
}

fun Profile.fromDTO(profileDTO: ProfileDTO) : Profile {
    val profileFromDTO : Profile = Profile()
    profileFromDTO.name = profileDTO.name
    profileFromDTO.surname = profileDTO.surname
    profileFromDTO.email = profileDTO.email
    return profileFromDTO
}