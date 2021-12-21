package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.presentation

import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfoList: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)