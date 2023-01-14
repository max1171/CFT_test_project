package com.lobzhanidze.cft_test_project.dugger

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lobzhanidze.cft_test_project.data.BinRepository
import com.lobzhanidze.cft_test_project.data.api.BinApi
import com.lobzhanidze.cft_test_project.domain.BinInteractor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppComponent {

    @Provides
    fun provideInitRetrofit(okHttpClient: OkHttpClient): BinApi = Retrofit.Builder()
        .baseUrl("https://lookup.binlist.net/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(BinApi::class.java)

    @Provides
    fun providesOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .build()
                )
            }
            .addInterceptor(logging)
            .build()
        return okHttpClient
    }

    @Provides
    fun providesBinInteractor(binApi: BinApi): BinInteractor {
        return BinInteractor(binApi)
    }

    @Provides
    fun providesBinRepository(binInteractor: BinInteractor): BinRepository = BinRepository(binInteractor)
}