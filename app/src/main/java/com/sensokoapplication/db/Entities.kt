package com.sensokoapplication.db

import androidx.room.*

@Entity(tableName = "all_boxes")
data class Transportbox(
    @PrimaryKey(autoGenerate = true)
    val boxId: Long,
    val label : String,
    val transportId: Long,
    var hasArrived : Boolean = false,
    var arrival : String ="",
    var isCurrent : Boolean = false
){
    constructor(label: String,transportId: Long,hasArrived: Boolean,arrival: String = ""):
            this(0,label, transportId, hasArrived, arrival)
    constructor(label: String,transportId: Long):
            this(0,label, transportId)
    constructor(label: String,transportId: Long, hasArrived: Boolean,arrival: String,isCurrent: Boolean):
            this(0,label, transportId,hasArrived,arrival,isCurrent)
}


@Entity
data class Kammer(
    @PrimaryKey(autoGenerate = true) val kammerId: Long,
    val parentBoxId:Long,
    var goalTemp: Float,
    var medizin: String){
    constructor( parentBoxId: Long,currentTemp: Float,medizin: String) : this(0,parentBoxId, currentTemp, medizin)
}


//Bezeichnungen richtig?
@Entity
data class Transport(
    @PrimaryKey(autoGenerate = true) val transportId: Long,
    val carrier : String,
    val transporter : String,
    val origin:String,
    val destination: String,
    var ready : String= "",
    var shipped: String = "",
    var arrival: String = "",
    var stops: Int = 100000
) {
    constructor(
        carrier: String,
        transporter: String,
        origin: String,
        destination: String
    ) : this(0,carrier, transporter, origin, destination)
}

@Entity
data class Parameter(
    @PrimaryKey(autoGenerate = true) val parameterId: Long,
    val parentKammerId: Long,
    val timestamp: String,
    val temperatur: Float
){
    constructor(
        parentKammerId: Long,timestamp: String,temperatur: Float
    ):this(0,parentKammerId,timestamp, temperatur)
}

//Relationships Entities

//1:n Box - Kammer
data class BoxWithKammern(
    @Embedded val transportbox: Transportbox,
    @Relation(
        parentColumn = "boxId",
        entityColumn = "parentBoxId",
    )
    val kammern: List<Kammer>
)

//1:n Kammer - Parameter
data class KammerWithParameter(
    @Embedded val kammer : Kammer,
    @Relation(
        parentColumn = "kammerId",
        entityColumn =  "parentKammerId"
    )
    val paramterList: List<Parameter>
)

