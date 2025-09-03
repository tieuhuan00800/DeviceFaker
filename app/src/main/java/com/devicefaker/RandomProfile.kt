package com.devicefaker

import kotlin.random.Random

object RandomProfile {

    private val hexChars = "0123456789abcdef"

    fun randomAndroidId() = (1..16).map { hexChars.random() }.joinToString("")
    fun randomImei() = (1..15).map { Random.nextInt(0, 9) }.joinToString("")
    fun randomSubscriberId() = "4600" + (1..11).map { Random.nextInt(0, 9) }.joinToString("")
    fun randomPhoneNumber() = "+84" + (1..9).map { Random.nextInt(0, 9) }.joinToString("")
    fun randomBuildModel() = listOf("Pixel 7 Pro", "Galaxy S23", "Xiaomi 13", "OnePlus 11").random()
    fun randomBuildBrand() = listOf("Google", "Samsung", "Xiaomi", "OnePlus").random()
    fun randomBuildManufacturer() = listOf("Google", "Samsung", "Xiaomi", "OnePlus").random()
    fun randomSerial() = (1..12).map { hexChars.random() }.joinToString("")
    fun randomMac() = (1..6).joinToString(":") { "%02x".format(Random.nextInt(0, 255)) }
    fun randomResolution() = listOf(1080 to 2400, 1440 to 3120, 1170 to 2532, 1080 to 2280).random()

    fun generateProfile(): Map<String, String> {
        val (w, h) = randomResolution()
        return mapOf(
            "android_id" to randomAndroidId(),
            "imei" to randomImei(),
            "subscriber_id" to randomSubscriberId(),
            "phone_number" to randomPhoneNumber(),
            "brand" to randomBuildBrand(),
            "model" to randomBuildModel(),
            "manufacturer" to randomBuildManufacturer(),
            "serial" to randomSerial(),
            "mac" to randomMac(),
            "screen_width" to w.toString(),
            "screen_height" to h.toString()
        )
    }
}
