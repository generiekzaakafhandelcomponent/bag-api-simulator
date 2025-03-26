package com.kadaster.bag_api.model

data class Links(
    val self: Link,
    val openbareRuimte: Link? = null,
    val nummeraanduiding: Link? = null,
    val woonplaats: Link? = null,
    val adresseerbaarObject: Link? = null,
    val panden: List<Link> = emptyList()
)

data class Link(
    val href: String,
    val templated: Boolean? = null,
    val title: String? = null
)
