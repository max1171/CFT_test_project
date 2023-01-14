package com.lobzhanidze.cft_test_project.data

import android.content.Context
import com.lobzhanidze.cft_test_project.data.db.BinDb
import com.lobzhanidze.cft_test_project.data.entity.BinModelDomain
import com.lobzhanidze.cft_test_project.data.entity.QueryBinHistory
import com.lobzhanidze.cft_test_project.domain.BinInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BinRepository @Inject constructor(
    private val interactor: BinInteractor
) {

    fun queryBinInformation(context: Context, bin: String) =
        interactor.searchBit(bin)
            .mapNotNull { binTransport ->
                val binDomain = BinModelDomain(
                    0,
                    binTransport.scheme,
                    binTransport.brand,
                    binTransport.number?.length.toString(),
                    transformBoolean(binTransport.number?.luhn.toString()),
                    binTransport.type,
                    transformBoolean(binTransport.prepaid.toString()),
                    binTransport.country?.emoji,
                    binTransport.country?.name,
                    binTransport.country?.latitude.toString(),
                    binTransport.country?.longitude.toString(),
                    binTransport.bank?.name,
                    binTransport.bank?.url,
                    binTransport.bank?.phone
                )
                BinDb.getInstance(context)?.getBinItemDao()?.clearTable()
                BinDb.getInstance(context)?.getBinItemDao()?.setBinItem(binDomain)
                BinDb.getInstance(context)?.getHistoryQueryBinDao()
                    ?.setQuery(QueryBinHistory(actualDate(), bin))
                BinDb.getInstance(context)?.getBinItemDao()?.getBinItem()
            }.flowOn(Dispatchers.Default)

    fun getHistoryQuery(context: Context) = flow {
        val list = BinDb.getInstance(context)?.getHistoryQueryBinDao()?.getQueryHistory()
        emit(list)
    }.flowOn(Dispatchers.Default)

    private fun actualDate(): String{
       return LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    }


    private fun transformBoolean(str: String): String {
        if (str == null) {
            return "-"
        }
        return if (str == "true") {
            "Yes"
        } else {
            "No"
        }
    }
}