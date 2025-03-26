package com.kadaster.bag_api.repository


import com.kadaster.bag_api.model.Address
import com.kadaster.bag_api.model.AddressResponse
import com.kadaster.bag_api.model.Embedded
import com.kadaster.bag_api.model.Geconstateerd
import com.kadaster.bag_api.model.InOnderzoek
import com.kadaster.bag_api.model.Link
import com.kadaster.bag_api.model.Links
import org.springframework.stereotype.Repository

@Repository
class AddressRepository {
    private val addresses = listOf(
        createAddress(
            openbareRuimteNaam = "Spui",
            korteNaam = "Spui",
            huisnummer = 70,
            huisletter = "A",
            postcode = "2511BT",
            woonplaatsNaam = "Funenpark",
            nummeraanduidingIdentificatie = "1234567890123456",
            openbareRuimteIdentificatie = "9876543210987654",
            woonplaatsIdentificatie = "5678",
            adresseerbaarObjectIdentificatie = "0518010000583529"
        ),
        createAddress(
            openbareRuimteNaam = "Dorpsstraat",
            korteNaam = "Dorpsstraat",
            huisnummer = 15,
            huisletter = "C",
            postcode = "2631CR",
            woonplaatsNaam = "Nootdorp",
            nummeraanduidingIdentificatie = "9876543210123456",
            openbareRuimteIdentificatie = "1234567890123456",
            woonplaatsIdentificatie = "4321",
            adresseerbaarObjectIdentificatie = "0518010000583530"
        )
    )

    fun findByPostcodeAndHuisnummer(
        postcode: String,
        huisnummer: Int,
        huisletter: String?,
        huisnummertoevoeging: String?
    ): AddressResponse? {
        val filteredAddresses = addresses.filter { address ->
            address.postcode == postcode && address.huisnummer == huisnummer &&
                    (huisletter == null || address.huisletter == huisletter) &&
                    (huisnummertoevoeging == null || address.huisnummertoevoeging == huisnummertoevoeging)
        }

        return filteredAddresses.takeIf { it.isNotEmpty() }?.let {
            AddressResponse(
                embedded = Embedded(it),
                links = Links(
                    self = Link("https://api.bag.nl/adressen")
                )
            )
        }
    }

    private fun createAddress(
        openbareRuimteNaam: String,
        korteNaam: String,
        huisnummer: Int,
        huisletter: String?,
        postcode: String,
        woonplaatsNaam: String,
        nummeraanduidingIdentificatie: String,
        openbareRuimteIdentificatie: String,
        woonplaatsIdentificatie: String,
        adresseerbaarObjectIdentificatie: String
    ): Address {
        return Address(
            openbareRuimteNaam = openbareRuimteNaam,
            korteNaam = korteNaam,
            huisnummer = huisnummer,
            huisletter = huisletter,
            huisnummertoevoeging = null,
            postcode = postcode,
            woonplaatsNaam = woonplaatsNaam,
            nummeraanduidingIdentificatie = nummeraanduidingIdentificatie,
            openbareRuimteIdentificatie = openbareRuimteIdentificatie,
            woonplaatsIdentificatie = woonplaatsIdentificatie,
            adresseerbaarObjectIdentificatie = adresseerbaarObjectIdentificatie,
            pandIdentificaties = listOf("9876543210123456"),
            indicatieNevenadres = false,
            adresregel5 = "$korteNaam $huisnummer ${huisletter.orEmpty()}",
            adresregel6 = "$postcode $woonplaatsNaam",
            geconstateerd = Geconstateerd(
                woonplaats = true,
                openbareRuimte = true,
                nummeraanduiding = true
            ),
            inonderzoek = InOnderzoek(
                openbareRuimteNaam = false,
                korteNaam = false,
                huisnummer = false,
                huisletter = false,
                huisnummertoevoeging = false,
                postcode = false,
                woonplaatsNaam = false,
                openbareRuimteLigtIn = false,
                openbareRuimteStatus = false,
                nummeraanduidingLigtIn = false,
                nummeraanduidingligtAan = false,
                nummeraanduidingStatus = false,
                toelichting = emptyList(),
                adresregel5 = false,
                adresregel6 = false
            ),
            links = Links(
                self = Link(href = "https://api.bag.nl/adressen/$nummeraanduidingIdentificatie"),
                openbareRuimte = Link(href = "https://api.bag.nl/openbareruimte/$openbareRuimteIdentificatie"),
                nummeraanduiding = Link(href = "https://api.bag.nl/nummeraanduiding/$nummeraanduidingIdentificatie"),
                woonplaats = Link(href = "https://api.bag.nl/woonplaats/$woonplaatsIdentificatie"),
                adresseerbaarObject = Link(href = "https://api.bag.nl/adresseerbaarobject/$adresseerbaarObjectIdentificatie"),
                panden = listOf(Link(href = "https://api.bag.nl/panden/9876543210123456"))
            )
        )
    }
}
