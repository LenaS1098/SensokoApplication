package com.sensokoapplication.mvvm

import com.sensokoapplication.BoxWithKammern
import com.sensokoapplication.Kammer
import com.sensokoapplication.Transportbox

interface BoxRepository {

    suspend fun getAllBoxes(): List<Transportbox>

    suspend fun getCurBox(): Transportbox

    suspend fun changeCurrentBox(newTransportbox : Transportbox)

    suspend fun addBox(transportbox: Transportbox)

    suspend fun getKammernFromBox(transportbox: Transportbox) : List<Kammer>

    suspend fun getKammern(): List<BoxWithKammern>
}