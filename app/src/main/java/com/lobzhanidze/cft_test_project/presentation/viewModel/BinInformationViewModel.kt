package com.lobzhanidze.cft_test_project.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lobzhanidze.cft_test_project.data.BinRepository
import com.lobzhanidze.cft_test_project.data.entity.BinModelDomain
import com.lobzhanidze.cft_test_project.data.entity.QueryBinHistory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BinInformationViewModel(
    private val binRepository: BinRepository,
) : ViewModel() {
    private val _binInformation = MutableSharedFlow<BinModelDomain>()
    val binInformation: SharedFlow<BinModelDomain> = _binInformation

    private val _binHistoryQuery = MutableSharedFlow<MutableList<QueryBinHistory>>()
    val binHistoryQuery: SharedFlow<MutableList<QueryBinHistory>> = _binHistoryQuery

    private val _resultStatus = MutableSharedFlow<Boolean>(0)
    val resultStatus: SharedFlow<Boolean> = _resultStatus


    fun loadBinInformation(context: Context, bin: String) {
        viewModelScope.launch {
            binRepository.queryBinInformation(context, bin)
                .catch {
                    _resultStatus.emit(false)
                }
                .collectLatest { binInformation ->
                    _resultStatus.emit(true)
                    _binInformation.emit(binInformation)
                }
        }
    }

    fun getHistoryQuery(context: Context) {
        viewModelScope.launch {
            binRepository.getHistoryQuery(context)
                .catch { Log.e("TAG", "getHistoryQuery: Список Пуст!", ) }
                .collectLatest { queryHistory ->
                    _binHistoryQuery.emit(queryHistory!!)
            }
        }
    }
}