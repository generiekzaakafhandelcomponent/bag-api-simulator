package com.bag.service

import com.bag.model.AddressResponse
import com.bag.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(private val addressRepository: AddressRepository) {

    fun getAddress(postcode: String, huisnummer: Int, huisletter: String?, huisnummertoevoeging: String?): AddressResponse? {
        return addressRepository.findByPostcodeAndHuisnummer(postcode, huisnummer, huisletter, huisnummertoevoeging)
    }
}
