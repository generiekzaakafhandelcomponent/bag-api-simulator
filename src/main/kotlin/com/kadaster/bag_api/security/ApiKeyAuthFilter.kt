package com.kadaster.bag_api.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class ApiKeyAuthFilter(
    private val environment: Environment,
    requestMatcher: RequestMatcher
) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val apiSecret = request.getHeader(API_KEY_HEADER)
            ?: throw BadCredentialsException("Missing API key header: $API_KEY_HEADER")

        val configuredKey = environment.getProperty("auth.api-key")
            ?: throw IllegalStateException("Missing configured auth.api-key")
        val token = ApiKeyAuthenticationToken(configuredKey, apiSecret)

        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        logger.info("Authentication successful for request: ${request.requestURI}")
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        logger.warn("Authentication failed for ${request.requestURI}: ${failed.message}\"")
        SecurityContextHolder.clearContext()
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.message)
    }

    companion object {
        private const val API_KEY_HEADER = "X-Api-Key"
    }
}
