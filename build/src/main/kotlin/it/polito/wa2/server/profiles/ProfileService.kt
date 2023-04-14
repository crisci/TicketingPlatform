package it.polito.wa2.server.profiles

interface ProfileService {

    fun getProfileByEmail(email: String) : ProfileDTO?

    fun getAll() : List<ProfileDTO>

    fun insertProfile(profileDTO: ProfileDTO)

    fun updateProfile(profileDTO: ProfileDTO, email: String)

}