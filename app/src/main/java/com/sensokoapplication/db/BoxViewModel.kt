package com.sensokoapplication.db

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BoxViewModel(private val boxRepository: BoxRepository) : ViewModel() {

    private val emptyBox = Transportbox("Empty",-1)
    private val emptyTransport = Transport("","db","falscher","aufruf")
    private val emptyKammer = Kammer(-1,-1.0F,"")

    val transportboxen : MutableState<List<Transportbox>> = mutableStateOf(listOf())
    val currentBox: MutableState<Transportbox> = mutableStateOf(emptyBox)
    val currentTransport : MutableState<Transport> = mutableStateOf(emptyTransport)
    val currentKammer: MutableState<Kammer> = mutableStateOf(emptyKammer)
    val listeKammern: MutableState<List<Kammer>> = mutableStateOf(listOf())

   // val transportById: MutableState<Transport> = mutableStateOf(emptyTransport)

    val transportList: MutableState<List<Transport>> = mutableStateOf(listOf())
    val transportCount: MutableState<Long> = mutableStateOf(-1)
    val boxCount: MutableState<Long> = mutableStateOf(-1)

    val listeParameter : MutableState<List<Parameter>> = mutableStateOf(listOf())

    val box = Transportbox(label = "rer",1,false,"",true)

    init {
        viewModelScope.launch {
            transportboxen.value = boxRepository.getAllBoxes()
            currentBox.value = boxRepository.getCurBox()
            listeKammern.value = boxRepository.getKammernFromBox(currentBox.value)
            currentTransport.value = boxRepository.getTransport(currentBox.value)
            transportCount.value = boxRepository.getTransportCount()
            boxCount.value = boxRepository.getBoxCount()
            currentKammer.value = listeKammern.value[0]
            transportList.value = boxRepository.getTransportList()
        }
    }

    fun getAllBoxes(){
        viewModelScope.launch {
            transportboxen.value = boxRepository.getAllBoxes()
        }
    }

    private fun updateData(){
        viewModelScope.launch {
            transportboxen.value = boxRepository.getAllBoxes()
            currentBox.value = boxRepository.getCurBox()
        }
    }

     fun changeCurBox(newTransportbox : Transportbox){
         viewModelScope.launch {
             currentBox.value = newTransportbox
             boxRepository.changeCurrentBox(newTransportbox)
             currentTransport.value = boxRepository.getTransport(newTransportbox)
             listeKammern.value = boxRepository.getKammernFromBox(newTransportbox)
             listeParameter.value = boxRepository.getParameterFromKammer(listeKammern.value[0].kammerId)
         }

    }

    fun changeCurKammer(newKammer: Kammer){
        viewModelScope.launch {
            currentKammer.value = newKammer
            listeParameter.value = boxRepository.getParameterFromKammer(newKammer.kammerId)
        }
    }

    fun insertBox(transportbox: Transportbox){
        viewModelScope.launch {
            boxRepository.addBox(transportbox)
            updateData()
            boxCount.value = boxCount.value+1

        }
    }

    fun insertTransport(transport: Transport){
        viewModelScope.launch {
            boxRepository.insertTransport(transport)
            transportCount.value = transportCount.value+1

        }
    }

    fun insertKammer(kammer: Kammer){
        viewModelScope.launch {
            boxRepository.insertKammer(kammer)
        }
    }


     fun getKammernFromBox(transportbox: Transportbox){
         viewModelScope.launch {
             listeKammern.value = boxRepository.getKammernFromBox(transportbox)
         }
    }

    fun getParameterFromKammer(transportbox: Transportbox, kammerIndex: Int){
        val allKammern = listeKammern.value
        val curKammer = allKammern[kammerIndex]
        viewModelScope.launch {
            listeParameter.value = boxRepository.getParameterFromKammer(curKammer.kammerId)
        }
    }


}