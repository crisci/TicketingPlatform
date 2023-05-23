package it.polito.wa2.ticketing.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val jwtAuthConverter: JwtAuthConverter) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**","/API/customers").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/customers/email=*","/API/customers").hasAnyRole(MANAGER,EXPERT)
            .requestMatchers(HttpMethod.PUT, "/API/tickets/*/*/stop").hasAnyRole(MANAGER,EXPERT)
            .anyRequest().authenticated()
        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }

    companion object {
        const val MANAGER = "Manager"
        const val EXPERT = "Expert"
        const val CLIENT = "Client"
    }
}