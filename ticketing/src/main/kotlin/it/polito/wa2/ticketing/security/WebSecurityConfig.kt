package it.polito.wa2.ticketing.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    val admin = "admin"
    val customer = "customer"
    val expert = "expert"
    private val jwtAuthConverter: JwtAuthConverter? = null

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        println("Ok Filter Chain")
        http.authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/test/customer").permitAll()
            .anyRequest().authenticated()
        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }

}