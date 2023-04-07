package it.polito.wa2.server.profiles

import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController



@RestController
class ProfileController(
    val profileService: ProfileService
) {

    @GetMapping("/API/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getProfileByEmail(@PathVariable email: String) : ProfileDTO? {
        if(profileService.getProfileByEmail(email) == null)
            throw ProfileNotFoundException("Profile not fount with the following email '${email}'")
        else
            return profileService.getProfileByEmail(email)
    }

    @GetMapping("/API/profiles/")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<ProfileDTO> {
        return profileService.getAll()
    }

    @PostMapping("/API/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun postProfile(@RequestBody profileDTO: ProfileDTO) {
            if(profileService.getProfileByEmail(profileDTO.email) == null)
                profileService.insertProfile(profileDTO)
            else
                throw DuplicatedEmailException("${profileDTO.email} is already used")
    }

    @PutMapping("/API/profiles/{email}") @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    fun putProfile(@RequestBody profileDTO: ProfileDTO, @PathVariable(name = "email") email: String) {
            if(profileService.getProfileByEmail(email) != null)
                profileService.updateProfile(profileDTO, email)
            else
                throw ProfileNotFoundException("Profile not fount with the following email '${email}'")

    }

}