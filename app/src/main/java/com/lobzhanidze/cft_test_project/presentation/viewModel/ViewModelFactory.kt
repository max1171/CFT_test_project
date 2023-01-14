package com.lobzhanidze.cft_test_project.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lobzhanidze.cft_test_project.data.BinRepository
import com.lobzhanidze.cft_test_project.domain.BinInteractor
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val repository: BinRepository
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BinRepository::class.java).newInstance(repository)
    }
}