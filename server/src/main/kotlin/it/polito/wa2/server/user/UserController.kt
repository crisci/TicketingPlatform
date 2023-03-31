package it.polito.wa2.server.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
/*
@RestController
class UserController(val userService:UserService) {
    @GetMapping("/API/profiles/{email}")
    fun getUser(@PathVariable("email") email:String)){
        userService.getUser(email)
    }

    @PostMapping("/API/profiles")
    @RequestBody
    fun addUser()
}*/