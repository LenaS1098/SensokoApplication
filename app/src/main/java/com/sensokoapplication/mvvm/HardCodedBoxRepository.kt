/*
package com.sensokoapplication.mvvm

import com.sensokoapplication.BoxWithKammern
import com.sensokoapplication.Kammer
import com.sensokoapplication.Transportbox
import com.sensokoapplication.db.BoxRepository


class HardCodedBoxRepository : BoxRepository {

    var currentTransportbox : Transportbox = Transportbox(-1,"Dummy","GM","K",false)

    override suspend fun getAllBoxes(): List<Transportbox> {
        return emptyList()
    }

    override suspend fun getCurBox(): Transportbox {
        return currentTransportbox
    }

    override suspend fun changeCurrentBox(newTransportbox: Transportbox) {
        currentTransportbox = newTransportbox
    }

    override suspend fun addBox(transportbox: Transportbox) {
        TODO("Not yet implemented")
    }

    override suspend fun getKammernFromBox(transportbox: Transportbox): List<Kammer> {
        TODO("Not yet implemented")
    }

    override suspend fun getKammern(): List<BoxWithKammern> {
        TODO("Not yet implemented")
    }

}*/
