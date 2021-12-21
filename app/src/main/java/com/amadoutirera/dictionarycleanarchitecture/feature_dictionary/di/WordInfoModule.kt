package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.local.Converters
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.local.WordInfoDatabase
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.remote.DictionaryApi
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.util.GsonParser
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.repository.WordInfoRepository
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.use_case.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    fun provideWordInfoRepository(dictionaryApi: DictionaryApi, db: WordInfoDatabase): WordInfoRepository{
        return WordInfoRepositoryImpl(dictionaryApi, db.dao)
    }

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(wordInfoRepository: WordInfoRepository): GetWordInfo{
        return GetWordInfo(wordInfoRepository)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase{
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi{
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}