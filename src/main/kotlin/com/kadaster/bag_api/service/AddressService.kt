package com.kadaster.bag_api.service

import com.kadaster.bag_api.model.AddressResponse
import com.kadaster.bag_api.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(private val addressRepository: AddressRepository) {

    fun getAddress(postcode: String, huisnummer: Int, huisletter: String?, huisnummertoevoeging: String?): AddressResponse? {
        return addressRepository.findByPostcodeAndHuisnummer(postcode, huisnummer, huisletter, huisnummertoevoeging)
    }
}
