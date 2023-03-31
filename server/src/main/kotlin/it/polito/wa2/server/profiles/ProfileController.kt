package it.polito.wa2.server.profiles

import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController



@RestController
class ProfileController(
    val profileService: ProfileService
) {

    @GetMapping("/API/profiles/{email}")
    fun getProfileByEmail(@PathVariable email: String) : ProfileDTO? {
        return profileService.getProfileByEmail(email)
    }

    @GetMapping("/API/profiles/")
    fun getAll() : List<ProfileDTO> {
        return profileService.getAll()
    }

    @PostMapping("/API/profiles")
    fun postProfile(@RequestBody profile: Profile?) {
        if(profile != null) profileService.insertProfile(profile)
    }

    @PutMapping("/API/profiles/{email}") @Transactional
    fun putProfile(@RequestBody profile: Profile?) {
        if(profile!=null) profileService.updateProfile(profile)
    }

}