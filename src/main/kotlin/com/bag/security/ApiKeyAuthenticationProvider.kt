package com.bag.security

import com.bag.config.AuthProperties
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class ApiKeyAuthenticationProvider(
    private val authProperties: AuthProperties
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val providedKey = authentication.principal as String
        val providedSecret = authentication.credentials as String

        if (providedKey == authProperties.apiKey && providedSecret == authProperties.apiSecret) {
            return ApiKeyAuthenticationToken(providedKey, providedSecret, emptyList(), true)
        }

        throw BadCredentialsException("Invalid API key or secret")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return ApiKeyAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
