package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.repository

import com.amadoutirera.dictionarycleanarchitecture.core.util.Resource
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.local.WordInfoDao
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.remote.DictionaryApi
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.model.WordInfo
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
    ): WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> {

        return flow {

            emit(Resource.Loading())

            val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
            emit(Resource.Loading(data = wordInfos))

            try {
                val remoteWordInfos = api.getWordInfo(word)
                dao.deleteWordInfos(remoteWordInfos.map { it.word })
                dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

            }catch (e: HttpException){
                emit(Resource.Error(
                    message = e.localizedMessage,
                    data = wordInfos)
                )
            }catch (e: IOException){
                emit(Resource.Error(
                    message = e.localizedMessage,
                    data = wordInfos)
                )
            }

            val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
            emit(Resource.Success(data = newWordInfos))
        }

    }

}