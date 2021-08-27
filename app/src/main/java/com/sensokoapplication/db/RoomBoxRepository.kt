package com.sensokoapplication.db

import kotlinx.coroutines.flow.MutableStateFlow


class RoomBoxRepository(private val boxDao: BoxDao) : BoxRepository {

    private val readAllData : MutableStateFlow<List<Transportbox>> = MutableStateFlow(boxDao.getAll())


    private val listDummyKammer = listOf(Kammer(1,2f,"fhg"))


    override suspend fun getAllBoxes(): List<Transportbox> {
        return readAllData.value
    }

    override suspend fun getCurBox(): Transportbox {
        return boxDao.getCurrentBox()
    }

    override suspend fun changeCurrentBox(newTransportbox: Transportbox) {
       val oldBox = getCurBox()
        oldBox.isCurrent = false
        updateBox(oldBox)
        newTransportbox.isCurrent = true
        updateBox(newTransportbox)
    }

    override suspend fun addBox(transportbox:Transportbox){
        boxDao.insertBox(transportbox)
    }

    override suspend fun getKammernFromBox(transportbox: Transportbox): List<Kammer> {
       val list=  boxDao.getKammernFromBox(transportbox.boxId)
        return if(list.first().kammern.isNotEmpty()){
           list.first().kammern
        }else{
            listDummyKammer
        }

    }

   /* private fun randomKammerList():List<Kammer>{
        val ausgangsliste = listOf(kammerA,kammerAB,kammerAC,kammerB,kammerC,kammerD)
        val ergebnisliste = listOf(ausgangsliste.random(),ausgangsliste.random(),ausgangsliste.random())
        return ergebnisliste
    }
*/
    override suspend fun getKammern(): List<BoxWithKammern> {
        return boxDao.getKammern()
    }


    suspend fun updateBox(transportbox: Transportbox){
        boxDao.update(transportbox)
    }

    suspend fun deleteBox(transportbox: Transportbox){
        boxDao.delete(transportbox)
    }

    suspend fun deleteAllBoxes(){
        boxDao.deleteAllBoxes()
    }

}