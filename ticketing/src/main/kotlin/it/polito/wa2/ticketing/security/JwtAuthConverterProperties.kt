package it.polito.wa2.ticketing.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated


@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
data class JwtAuthConverterProperties (
    var resourceId: String? = null,
    var principalAttribute: String? = null
)