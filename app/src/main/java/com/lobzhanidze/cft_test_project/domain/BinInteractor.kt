package com.lobzhanidze.cft_test_project.domain

import com.lobzhanidze.cft_test_project.data.api.BinApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BinInteractor @Inject constructor(
    private val binApi: BinApi
) {

    fun searchBit(bin: String) = flow {
        val binTransport = binApi.getBinInformation(bin)
        emit(binTransport)
    }.flowOn(Dispatchers.IO)
}