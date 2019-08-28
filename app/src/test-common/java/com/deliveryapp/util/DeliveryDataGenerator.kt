package com.deliveryapp.util

import com.deliveryapp.models.DeliveryData
import com.deliveryapp.models.Location
import okhttp3.mockwebserver.MockResponse
import okio.Okio
import java.io.InputStream

object DeliveryDataGenerator {
    private const val descTest = "Deliver assignment"
    private const val latTest = "22.12"
    private const val lngTest = "114.22"
    private const val addressTest = "Earth"
    private const val urlImgTest = "https://s3-ap-southeast-1.amazonaws.com/lalamove-mock-api/images/pet-1.jpeg"

    fun createList(count: Int): List<DeliveryData> {
        return createDeliveryDataList(count, descTest, urlImgTest, latTest, lngTest, addressTest)
    }

    fun createDeliveryDataList(count: Int, description: String, imageUrl: String?, lat: String, lng: String, address: String): List<DeliveryData> {
        val aa = arrayListOf<DeliveryData>()
        for (i in 0 until count) {
            aa.add(createDeliveryData(i, description, imageUrl, lat, lng, address))
        }
        return aa
    }

    fun createSingleDeliveryData(id: Int): DeliveryData {
        return createDeliveryData(id, descTest, urlImgTest, latTest, lngTest, addressTest)
    }

    fun createDeliveryData(id: Int, description: String, imageUrl: String?, lat: String, lng: String, address: String): DeliveryData {
        return DeliveryData(id, description, imageUrl, createLocationData(lat, lng, address))
    }

    private fun createLocationData(lat: String, lng: String, address: String): Location {
        return Location(lat.toDouble(), lng.toDouble(), address)
    }

    fun createMockResponse(fileName: String, responseCode: Int): MockResponse {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(responseCode)
        mockResponse.setBody(getLocationDataFromFile(fileName))
        return mockResponse
    }

    private fun getLocationDataFromFile(fileName: String): String  {
        val source = Okio.buffer(Okio.source(createInputStream(fileName)!!))
        return source.readString(Charsets.UTF_8)
    }

    private fun createInputStream(fileName: String): InputStream? {
        return javaClass.classLoader?.getResourceAsStream("mock_response/$fileName")
    }
}