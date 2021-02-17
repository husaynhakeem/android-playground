package com.husaynhakeem.datastoresample

data class Person(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val phoneNumber: PhoneNumber
)

data class Address(val street: String, val city: String, val country: String, val zipCode: Int)

data class PhoneNumber(val countryCode: Int, val number: Int)