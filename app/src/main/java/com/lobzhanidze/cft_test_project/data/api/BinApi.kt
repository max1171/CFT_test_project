package com.lobzhanidze.cft_test_project.data.api

import com.lobzhanidze.cft_test_project.data.entity.BinModelTransport
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {
    @GET("{bin}")
    suspend fun getBinInformation(@Path("bin") bin: String): BinModelTransport
}