package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.model


import com.google.gson.annotations.SerializedName

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)