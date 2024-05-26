package com.example.tfgapplibros.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class BusquedaViewModel: ViewModel() {

    //creamos una variable searchtext
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _libro = MutableStateFlow(listOf<Libro>())
    val libro = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_libro) {
        text, libro ->
        if(text.isBlank()){
            libro
        } else{
            delay(2000L)
            libro.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000),
            _libro.value
        )

    private val _usuario = MutableStateFlow(listOf<Usuario>())

   fun onSearchTextChange(text: String){
       _searchText.value = text
   }

}