package com.wilsonhernandez.interrrapidisimo.di

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.wilsonhernandez.interrrapidisimo.core.RetrofitHelper
import com.wilsonhernandez.interrrapidisimo.data.database.DatabaseSqlite
import com.wilsonhernandez.interrrapidisimo.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Provides
    @Singleton
    fun provideDatabase(context: Context): DatabaseSqlite {
        return DatabaseSqlite(context = context,name = "admin_db",null,1)
    }
    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return RetrofitHelper.getRetrofit()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}