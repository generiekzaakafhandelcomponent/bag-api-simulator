package com.kadaster.bag_api.model

// Links Model
data class Links(
    val self: Link,
    val openbareRuimte: Link?,
    val nummeraanduiding: Link?,
    val woonplaats: Link?,
    val adresseerbaarObject: Link?,
    val panden: List<Link> = emptyList()
)

data class Link(
    val href: String,
    val templated: Boolean? = null,
    val title: String? = null
)
