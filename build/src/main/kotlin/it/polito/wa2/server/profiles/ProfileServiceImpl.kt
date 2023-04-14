package it.polito.wa2.server.profiles

import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository
) : ProfileService {
    override fun getProfileByEmail(email: String): ProfileDTO? {
        return profileRepository.findProfilesByEmail(email)?.toDTO()
    }

    override fun getAll(): List<ProfileDTO> {
        return profileRepository.findAll().map { it.toDTO() }
    }

    override fun insertProfile(profileDTO: ProfileDTO) {
        profileRepository.save(Profile().fromDTO(profileDTO))
    }


    //the body of the request must contain all the fields
    override fun updateProfile(profileDTO: ProfileDTO, email: String) {
        val profileToUpdate: Profile? = profileRepository
            .findProfilesByEmail(email)
            ?.also {
                it.email = profileDTO.email
                it.name = profileDTO.name
                it.surname = profileDTO.surname
            }
        profileRepository.save(profileToUpdate!!)
    }


}