package com.sensokoapplication.db

interface BoxRepository {

    suspend fun getAllBoxes(): List<Transportbox>

    suspend fun getCurBox(): Transportbox

    suspend fun changeCurrentBox(newTransportbox : Transportbox)

    suspend fun addBox(transportbox: Transportbox)

    suspend fun getKammernFromBox(transportbox: Transportbox) : List<Kammer>

    suspend fun getKammern(): List<BoxWithKammern>

    suspend fun getTransport(transportbox: Transportbox):Transport

    suspend fun getParameterFromKammer(kammerId:Long):List<Parameter>

    suspend fun insertTransport(transport: Transport)

    suspend fun insertKammer(kammer: Kammer)

    suspend fun insertParameter(parameter: Parameter)

    suspend fun getTransportId(carrier : String, transporter: String, origin: String, destination: String):Long

    suspend fun getBoxId(label:String):Long

    suspend fun getBoxCount():Long

    suspend fun getTransportCount():Long

    suspend fun getKammerId(parentBoxId:Long, goalTemp:Float):Long

    suspend fun getTransportFromId(tranportId:Long):Transport
}