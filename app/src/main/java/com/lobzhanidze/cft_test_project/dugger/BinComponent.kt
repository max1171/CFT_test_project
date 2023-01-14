package com.lobzhanidze.cft_test_project.dugger

import android.app.Application
import com.lobzhanidze.cft_test_project.presentation.view.SearchBinFragment
import dagger.Component

@Component(modules = [AppComponent::class])
interface BinComponent {
    fun inject(app: Application)
    fun inject(searchBinFragment: SearchBinFragment)
}