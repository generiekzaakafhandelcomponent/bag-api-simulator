package com.kadaster.bag_api.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${auth.username}")
    lateinit var username: String

    @Value("\${auth.password}")
    lateinit var password: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                // changed the api permission for test purpose
                auth.requestMatchers("/api/adressen").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .anyRequest().permitAll()
            }
            .httpBasic { }
            .csrf { csrf -> csrf.disable() }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user: UserDetails = User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}
