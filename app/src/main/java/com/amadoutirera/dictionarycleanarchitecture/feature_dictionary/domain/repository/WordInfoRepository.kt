package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.repository

import com.amadoutirera.dictionarycleanarchitecture.core.util.Resource
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}