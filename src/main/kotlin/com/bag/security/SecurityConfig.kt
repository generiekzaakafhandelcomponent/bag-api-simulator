package com.bag.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val apiKeyAuthenticationProvider: ApiKeyAuthenticationProvider,
    private val environment: Environment
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/haalcentraal/api/**").authenticated()
                    .anyRequest().permitAll()
            }
            .authenticationManager(authenticationManager)
            .addFilterBefore(apiKeyAuthFilter(authenticationManager), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun apiKeyAuthFilter(authenticationManager: AuthenticationManager): ApiKeyAuthFilter {
        return ApiKeyAuthFilter(environment, AntPathRequestMatcher("/haalcentraal/api/**")).apply {
            setAuthenticationManager(authenticationManager)
        }
    }

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationProvider(apiKeyAuthenticationProvider)
            .build()
    }
}
