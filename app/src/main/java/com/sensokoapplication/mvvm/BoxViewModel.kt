package com.sensokoapplication.mvvm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sensokoapplication.Kammer
import com.sensokoapplication.Transportbox
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BoxViewModel(private val boxRepository: BoxRepository) : ViewModel() {

    private val emptyBox = Transportbox(-1,"Empty","Empty","Empty",false,"",true)

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

    fun getAllBoxes():MutableState<List<Transportbox>>{
        return transportboxen
    }

     fun changeCurBox(newTransportbox : Transportbox){
         viewModelScope.launch {
             boxRepository.changeCurrentBox(newTransportbox)
         }
         currentBox.value = newTransportbox
    }

     fun getKammernFromBox(transportbox: Transportbox) : List<Kammer>{
        var ergListe = emptyList<Kammer>()
         viewModelScope.launch {
             ergListe = boxRepository.getKammernFromBox(transportbox)
         }
        return ergListe
    }
}