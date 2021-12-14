package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.remote.dto


import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.data.local.entity.WordInfoEntity
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.model.WordInfo
import com.google.gson.annotations.SerializedName

data class WordInfoDto(
    @SerializedName("meanings")
    val meanings: List<MeaningDto>,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("phonetic")
    val phonetic: String,
    @SerializedName("phonetics")
    val phonetics: List<PhoneticDto>,
    @SerializedName("word")
    val word: String
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }

    fun toWordInfoEntity(): WordInfoEntity{
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }
}

