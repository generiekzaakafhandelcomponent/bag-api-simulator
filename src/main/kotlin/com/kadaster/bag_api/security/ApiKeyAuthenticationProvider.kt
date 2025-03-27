package com.kadaster.bag_api.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class ApiKeyAuthenticationProvider(
    @Value("\${auth.api-key}") private val configuredApiKey: String,
    @Value("\${auth.api-secret}") private val configuredApiSecret: String
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val providedKey = authentication.principal as String
        val providedSecret = authentication.credentials as String

        if (providedKey == configuredApiKey && providedSecret == configuredApiSecret) {
            return ApiKeyAuthenticationToken(providedKey, providedSecret, emptyList(), true)
        }

        throw BadCredentialsException("Invalid API key or secret")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return ApiKeyAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
