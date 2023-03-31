package it.polito.wa2.server.profiles

interface ProfileService {

    fun getProfileByEmail(email: String) : ProfileDTO?

    fun getAll() : List<ProfileDTO>

    fun insertProfile(profile: Profile?)

    fun updateProfile(profile: Profile?)

}