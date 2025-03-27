package com.kadaster.bag_api.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class ApiKeyAuthenticationToken(
    private val apiKey: String,
    private val apiSecret: String,
    authorities: Collection<GrantedAuthority>,
    isAuthenticated: Boolean
) : AbstractAuthenticationToken(authorities) {

    init {
        super.setAuthenticated(isAuthenticated)
    }

    constructor(apiKey: String, apiSecret: String) : this(apiKey, apiSecret, emptyList(), false)

    override fun getCredentials(): Any = apiSecret
    override fun getPrincipal(): Any = apiKey

}

