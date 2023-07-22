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
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
class WebSecurityConfig(private val jwtAuthConverter: JwtAuthConverter) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/API/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/API/login", "/API/signup", "/API/refresh").permitAll()
            .requestMatchers(HttpMethod.PUT, "/API/tickets/*/close").authenticated()
            .requestMatchers(HttpMethod.GET, "/API/tickets/*/messages","/API/tickets/*/status").authenticated()
            .requestMatchers(HttpMethod.GET, "/API/tickets/*/expert","/API/tickets/*/history").authenticated()
            .requestMatchers(HttpMethod.GET, "/API/messages", "/API/attachments").authenticated()
            .requestMatchers(HttpMethod.GET, "/API/customers/products", "/API/customers/tickets",
                "/API/customers/tickets/*/messages").hasRole(CLIENT)
            .requestMatchers(HttpMethod.POST, "/API/customers/product", "/API/customers/tickets/*/messages",
                "/API/customers/tickets").hasRole(CLIENT)
            .requestMatchers(HttpMethod.PUT, "/API/customers/tickets/*/resolved", "/API/customers/tickets/*/reopen",
                "/API/customers/tickets/*/resolved", "/API/customers/tickets/*/reopen", "/API/customers/tickets/*/close").hasRole(CLIENT)
            .requestMatchers(HttpMethod.DELETE, "/API/customers/product").hasRole(CLIENT)
            .requestMatchers(HttpMethod.PUT,"/API/expert/*/stop").hasRole(EXPERT)
            .requestMatchers(HttpMethod.GET,"/API/expert/tickets").hasRole(EXPERT)
            .requestMatchers(HttpMethod.POST, "/API/expert/tickets/*/messages").hasRole(EXPERT)
            .requestMatchers(HttpMethod.PUT, "/API/manager/tickets/*/reassign").hasRole(MANAGER)
            .requestMatchers(HttpMethod.POST, "/API/messages/*/attachments").hasAnyRole(CLIENT,EXPERT)
            .requestMatchers(HttpMethod.PUT, "/API/messages/*").hasAnyRole(CLIENT,EXPERT)
            .requestMatchers(HttpMethod.POST, "/API/createExpert").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**","/API/customers").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/customers/email=*","/API/customers").hasAnyRole(MANAGER,EXPERT)
            .requestMatchers(HttpMethod.GET, "/API/manager/tickets").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/manager/customers").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/manager/expert","/API/manager/experts").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/manager/tickets/*/messages").hasRole(MANAGER)
            .requestMatchers(HttpMethod.PUT, "/API/manager/tickets/*/assign").hasRole(MANAGER)
            .requestMatchers(HttpMethod.PUT, "/API/manager/experts/*/approve").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/products/","/API/products/*", "/API/products/*/tickets").hasRole(MANAGER)
            .requestMatchers(HttpMethod.POST, "/API/products").hasRole(MANAGER)
            .requestMatchers(HttpMethod.PUT, "/API/products/*").hasRole(MANAGER)
            .requestMatchers(HttpMethod.DELETE, "/API/products/*").hasRole(MANAGER)
            .requestMatchers(HttpMethod.GET, "/API/products/*/tickets").hasRole(MANAGER)
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

