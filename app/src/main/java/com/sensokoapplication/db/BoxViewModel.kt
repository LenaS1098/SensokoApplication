package com.sensokoapplication.db

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BoxViewModel(private val boxRepository: BoxRepository) : ViewModel() {

    private val emptyBox = Transportbox("Empty",-1)

    private val transportboxen : MutableState<List<Transportbox>> = mutableStateOf(listOf())
    private val currentBox: MutableState<Transportbox> = mutableStateOf(emptyBox)

    init {
        viewModelScope.launch {
            transportboxen.value = boxRepository.getAllBoxes()
            currentBox.value = boxRepository.getCurBox()
        }
    }

      fun getCurBox(): Transportbox{
        return currentBox.value
    }

    fun getAllBoxes():List<Transportbox>{
        var erg:List<Transportbox> = emptyList()
        viewModelScope.launch {
            erg = boxRepository.getAllBoxes()
        }
        return erg
    }

     fun changeCurBox(newTransportbox : Transportbox){
         viewModelScope.launch {
             boxRepository.changeCurrentBox(newTransportbox)
         }
         currentBox.value = newTransportbox
    }

    fun insertBox(transportbox: Transportbox){
        viewModelScope.launch {
            boxRepository.addBox(transportbox)
        }
    }

     fun getKammernFromBox(transportbox: Transportbox) : List<Kammer>{
        var ergListe = emptyList<Kammer>()
         viewModelScope.launch {
             ergListe = boxRepository.getKammernFromBox(transportbox)
         }
        return ergListe
    }
}