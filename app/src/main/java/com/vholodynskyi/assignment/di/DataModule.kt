package com.vholodynskyi.assignment.di

import androidx.room.Room
import com.squareup.moshi.Moshi
import com.vholodynskyi.assignment.BuildConfig
import com.vholodynskyi.assignment.data.local.contact.ContactDatabase
import com.vholodynskyi.assignment.data.local.contact.LocalDataSource
import com.vholodynskyi.assignment.data.local.contact.LocalDataSourceImpl
import com.vholodynskyi.assignment.data.mapper.ContactMapper
import com.vholodynskyi.assignment.data.remote.contact.ContactService
import com.vholodynskyi.assignment.data.repository.ContactRepository
import com.vholodynskyi.assignment.data.repository.ContactRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    /////////////////////////////////////////////// REMOTE //////////////////////////////////////////////
    single {
        val client = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logger =
                HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logger)
        }
        client.build()
    }

    factory<ContactService> { get<Retrofit>().create(ContactService::class.java) }

    single<Moshi> {
        Moshi.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(getProperty("base"))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    /////////////////////////////////////////////// LOCAL //////////////////////////////////////////////

    single { get<ContactDatabase>().userDao() }

    single<LocalDataSource> {
        LocalDataSourceImpl(dao = get())
    }

    single {
        Room.databaseBuilder(
            get(),
            ContactDatabase::class.java,
            "app-database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    /////////////////////////////////////////////// REPOSITORY //////////////////////////////////////////////

    factory {
        ContactMapper()
    }

    factory<ContactRepository> {
        ContactRepositoryImpl(service = get(), localDataSource = get(), mapper = get())
    }
}