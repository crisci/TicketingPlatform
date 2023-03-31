package it.polito.wa2.server.profiles

import it.polito.wa2.server.products.ProductRepository
import it.polito.wa2.server.products.toDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository
) : ProfileService {
    override fun getProfileByEmail(email: String): ProfileDTO? {
        return profileRepository.findProfilesByEmail(email).toDTO()
    }

    override fun getAll(): List<ProfileDTO> {
        return profileRepository.findAll().map { it.toDTO() }
    }

    override fun insertProfile(profile: Profile?) {
        profileRepository.save(profile!!)
    }


    override fun updateProfile(profile: Profile?) {
        if(profile == null) return
        println(profile.email)
        val profileToUpdate: Profile = profileRepository.findProfilesByEmail(profile.email)
        profileToUpdate.name = profile.name
        profileToUpdate.surname = profile.surname
        profileRepository.save(profileToUpdate)
    }


}