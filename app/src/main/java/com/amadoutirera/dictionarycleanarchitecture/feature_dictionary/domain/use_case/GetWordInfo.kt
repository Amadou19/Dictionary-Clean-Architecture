package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.use_case

import com.amadoutirera.dictionarycleanarchitecture.core.util.Resource
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.model.WordInfo
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordInfo @Inject constructor(private val repository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        //return
        return if (word.isBlank()){
            flow {  }
        }
        else{
            repository.getWordInfo(word)
        }
    }
}