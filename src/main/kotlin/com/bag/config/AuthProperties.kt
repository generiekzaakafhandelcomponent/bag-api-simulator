package com.bag.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "auth")
class AuthProperties {
    lateinit var apiKey: String
    lateinit var apiSecret: String
}
