package com.kadaster.bag_api.repository


import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kadaster.bag_api.model.Address
import com.kadaster.bag_api.model.AddressResponse
import com.kadaster.bag_api.model.Embedded
import com.kadaster.bag_api.model.Geconstateerd
import com.kadaster.bag_api.model.InOnderzoek
import com.kadaster.bag_api.model.Link
import com.kadaster.bag_api.model.Links
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Repository

@Repository
class AddressRepository {

    private val addresses: List<Address> by lazy { loadAddressesFromJson() }

    fun findByPostcodeAndHuisnummer(
        postcode: String,
        huisnummer: Int,
        huisletter: String?,
        huisnummertoevoeging: String?
    ): AddressResponse? {
        val matchingAddresses = addresses.filter { address ->
            address.postcode == postcode &&
                    address.huisnummer == huisnummer &&
                    (huisletter == null || address.huisletter == huisletter) &&
                    (huisnummertoevoeging == null || address.huisnummertoevoeging == huisnummertoevoeging)
        }

        return if (matchingAddresses.isNotEmpty()) {
            AddressResponse(
                embedded = Embedded(matchingAddresses),
                links = Links(self = Link("https://api.bag.nl/adressen"))
            )
        } else {
            null
        }
    }

    private fun loadAddressesFromJson(): List<Address> {
        val resource = requireNotNull(this::class.java.getResource("/fixtures/address.json")) {
            "Address JSON file not found in classpath"
        }
        val typeRef = object : TypeReference<List<Address>>() {}
        return objectMapper.readValue(resource, typeRef)
    }

    companion object {
        private val logger = KotlinLogging.logger {}
        private val objectMapper = jacksonObjectMapper().findAndRegisterModules()
    }
}
