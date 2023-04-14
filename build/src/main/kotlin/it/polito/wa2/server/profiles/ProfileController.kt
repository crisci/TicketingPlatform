package it.polito.wa2.server.profiles

import it.polito.wa2.server.utils.EmailValidationUtil
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class ProfileController(
    val profileService: ProfileService
) {

    private val emailValidator = EmailValidationUtil()

    @GetMapping("/API/profiles/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun getProfileByEmail(@PathVariable email: String) : ProfileDTO? {
        if(profileService.getProfileByEmail(email.lowercase()) == null)
            throw ProfileNotFoundException("Profile not fount with the following email '${email}'")
        else
            return profileService.getProfileByEmail(email.lowercase())
    }

    @GetMapping("/API/profiles/")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<ProfileDTO> {
        return profileService.getAll()
    }

    @PostMapping("/API/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    fun postProfile(@RequestBody profileDTO: ProfileDTO) {
        if(profileDTO.email.isNotBlank() && profileDTO.name.isNotBlank() && profileDTO.surname.isNotBlank()) {
            if (emailValidator.checkEmail(profileDTO.email)) {
                if (profileService.getProfileByEmail(profileDTO.email.lowercase()) == null)
                    profileService.insertProfile(profileDTO)
                else {
                    throw DuplicatedEmailException("${profileDTO.email.lowercase()} is already used")
            }} else {
                throw InvalidEmailFormatException("Invalid email format")
            }} else {
            throw BlankFieldsException("Fields must not be blank")
        }
    }

    @PutMapping("/API/profiles/{email}") @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    fun putProfile(@RequestBody profileDTO: ProfileDTO, @PathVariable(name = "email") email: String) {
        if(profileDTO.email.isNotBlank() && profileDTO.name.isNotBlank() && profileDTO.surname.isNotBlank()) {
            if (emailValidator.checkEmail(profileDTO.email)) {
                if (profileService.getProfileByEmail(email.lowercase()) != null) {
                    if (profileService.getProfileByEmail(profileDTO.email.lowercase()) == null) profileService.updateProfile(
                        profileDTO,
                        email.lowercase()
                    )
                    else {
                        throw DuplicatedEmailException("${profileDTO.email.lowercase()} is already used")
                    }} else {
                    throw ProfileNotFoundException("Profile not fount with the following email '${email.lowercase()}'")
                }} else {
                throw InvalidEmailFormatException("Invalid email format")
            }} else {
            throw BlankFieldsException("Fields must not be blank")
        }
    }
}