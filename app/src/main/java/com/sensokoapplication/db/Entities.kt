package com.sensokoapplication.db

import androidx.room.*

@Entity(tableName = "all_boxes")
data class Transportbox(
    @PrimaryKey(autoGenerate = true)
    var boxId: Long = 0L,
    @ColumnInfo(name = "Boxkennung")
    val label : String,
    @ColumnInfo(name = "Transportinfos")
    val transportId: Long,
    @ColumnInfo(name = "Ist Angekommen")
    var hasArrived : Boolean = false,
    @ColumnInfo(name = "Ankunftszeit")
    var arrival : String ="",
    @ColumnInfo(name= "IsCurrentBox")
    var isCurrent : Boolean = false
){
    constructor(label: String,transportId: Long,hasArrived: Boolean,arrival: String = ""):
            this(-1,label, transportId, hasArrived, arrival)
    constructor(label: String,transportId: Long):
            this(-1,label, transportId)
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
    @PrimaryKey(autoGenerate = true) val transportId: Long = 0L,
    @ColumnInfo(name="Transporteur")
    val carrier : String,
    @ColumnInfo(name="Transporter")
    val transporter : String,
    @ColumnInfo(name = "Start")
    val origin:String,
    @ColumnInfo(name = "Ziel")
    val destination: String,
    @ColumnInfo(name = "Ready For Use")
    var ready : String= "",
    @ColumnInfo(name = "Shipping Time")
    var shipped: String = "",
    @ColumnInfo(name = "Arrival Time")
    var arrival: String = "",
    @ColumnInfo(name = "Stops")
    var stops: Int = 100000
) {
    constructor(
        carrier: String,
        transporter: String,
        origin: String,
        destination: String
    ) : this(-1,carrier, transporter, origin, destination)
}

@Entity
data class Parameter(
    @PrimaryKey(autoGenerate = true) val parameterId: Long = 0L,
    val parentKammerId: Long,
    @ColumnInfo(name="TimeStamp")
    val timestamp: String,
    @ColumnInfo(name="Temperatur")
    val temperatur: Float
){
    constructor(
        parentKammerId: Long,timestamp: String,temperatur: Float
    ):this(-1,parentKammerId,timestamp, temperatur)
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

