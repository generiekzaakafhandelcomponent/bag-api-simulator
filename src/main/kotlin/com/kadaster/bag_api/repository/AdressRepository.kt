package com.kadaster.bag_api.repository


import com.kadaster.bag_api.model.Address
import com.kadaster.bag_api.model.AddressResponse
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
            huisletter = "B",
            postcode = "2511BT",
            woonplaatsNaam = "'s-Gravenhage",
            nummeraanduidingIdentificatie = "1234567890123456",
            openbareRuimteIdentificatie = "9876543210987654",
            woonplaatsIdentificatie = "5678",
            adresseerbaarObjectIdentificatie = "1122334455667788"
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
            adresseerbaarObjectIdentificatie = "5566778899001122"
        )
    )

    fun findByPostcodeAndHuisnummer(
        postcode: String,
        huisnummer: Int,
        huisletter: String?,
        huisnummertoevoeging: String?
    ): AddressResponse? {
        return addresses.filter { it.postcode == postcode && it.huisnummer == huisnummer &&
                    (huisletter == null || it.huisletter == huisletter) &&
                    (huisnummertoevoeging == null || it.huisnummertoevoeging == huisnummertoevoeging)
        }
            .takeIf { it.isNotEmpty() }
            ?.let { AddressResponse(adressen = it) }
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
            geconstateerd = Geconstateerd(woonplaats = true, openbareRuimte = true, nummeraanduiding = true),
            inonderzoek = createDefaultInOnderzoek(),
            links = createLinks()
        )
    }

    private fun createDefaultInOnderzoek() = InOnderzoek(
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
        adresregel5 = false,
        adresregel6 = false
    )

    private fun createLinks() = Links(
        self = Link(href = "https://api.bag.nl/adressen/1"),
        openbareRuimte = Link(href = "https://api.bag.nl/openbareruimte/1"),
        nummeraanduiding = Link(href = "https://api.bag.nl/nummeraanduiding/1"),
        woonplaats = Link(href = "https://api.bag.nl/woonplaats/1"),
        adresseerbaarObject = Link(href = "https://api.bag.nl/adresseerbaarobject/1"),
        panden = listOf(Link(href = "https://api.bag.nl/panden/1"))
    )
}
