package com.sensokoapplication.db


class RoomBoxRepository(private val boxDao: BoxDao, private val transportDao: TransportDao, private val kammerDao: KammerDao, private val parameterDao: ParameterDao) : BoxRepository {

    private val listDummyKammer = listOf(Kammer(1,2f,"fhg"))

    override suspend fun getAllBoxes(): List<Transportbox> {
        return boxDao.getAll()
    }

    override suspend fun getCurBox(): Transportbox {
        return boxDao.getCurrentBox(true)
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
        if(list.isEmpty()){
            return listDummyKammer
        }
        return if(list.first().kammern.isNotEmpty() && list.isNotEmpty()){
           list.first().kammern
        }else{
            listDummyKammer
        }
    }

    override suspend fun getKammern(): List<BoxWithKammern> {
        return boxDao.getKammern()
    }

    override suspend fun getTransport(transportbox: Transportbox): Transport {
        return transportDao.getTransportById(transportbox.transportId)
    }

    override suspend fun getParameterFromKammer(kammerId: Long): List<Parameter> {
        return boxDao.getParameterFomKammer(kammerId)
    }

    override suspend fun insertTransport(transport: Transport) {
        transportDao.insertTransport(transport)
    }

    override suspend fun insertKammer(kammer: Kammer) {
        kammerDao.insertKammer(kammer)
    }

    override suspend fun insertParameter(parameter: Parameter) {
        boxDao.insertParameter(parameter)
    }

    override suspend fun getTransportId(
        carrier: String,
        transporter: String,
        origin: String,
        destination: String
    ): Long {
       return transportDao.getTransportId(carrier, transporter, origin, destination)
    }

    override suspend fun getBoxId(label: String): Long {
       return boxDao.getBoxId(label)
    }

    override suspend fun getBoxCount(): Long {
        return boxDao.getBoxCount()
    }

    override suspend fun getTransportCount(): Long {
        return transportDao.getTransportCount()
    }

    override suspend fun getKammerId(parentBoxId: Long, goalTemp: Float): Long {
        return kammerDao.getKammerId(parentBoxId, goalTemp)
    }

    override suspend fun getTransportFromId(tranportId: Long) : Transport {
        return transportDao.getTransportById(tranportId)
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