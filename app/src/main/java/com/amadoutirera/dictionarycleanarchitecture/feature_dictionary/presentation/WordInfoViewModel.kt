package com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadoutirera.dictionarycleanarchitecture.core.util.Resource
import com.amadoutirera.dictionarycleanarchitecture.feature_dictionary.domain.use_case.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WordInfoViewModel @Inject constructor( private val getWordInfo: GetWordInfo): ViewModel(){

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: MutableState<WordInfoState> = _state

    private val _evenFlow = MutableSharedFlow<UiEven>()
    val evenFlow: SharedFlow<UiEven> = _evenFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String){
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfo(query).onEach { result ->

                when(result){

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            wordInfoList = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            wordInfoList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            wordInfoList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _evenFlow.emit(UiEven.ShowSnackBar( result.message ?: "Unknown error"))
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UiEven(){
        class ShowSnackBar(val message: String): UiEven()
    }


}