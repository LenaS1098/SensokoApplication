package com.sensokoapplication

import androidx.room.*

@Entity(tableName = "all_boxes")
data class Transportbox(
    @PrimaryKey(autoGenerate = true)
    var boxId: Long = 0L,
    @ColumnInfo(name = "Boxkennung")
    val label : String,
    @ColumnInfo(name = "Start")
    var origin:String,
    @ColumnInfo(name = "Ziel")
    var destination: String,
    @ColumnInfo(name = "Ist Angekommen")
    var hasArrived : Boolean,
    @ColumnInfo(name = "Ankunftszeit")
    var arrival : String ="",
    @ColumnInfo(name= "IsCurrentBox")
    var isCurrent : Boolean = false
){
    constructor(label: String,origin: String,destination: String,hasArrived: Boolean,arrival: String = ""):this(-1,label, origin, destination, hasArrived, arrival)
}


@Entity
data class Kammer(
    @PrimaryKey(autoGenerate = true) val kammerId: Long,
    val parentBoxId:Long,
    var currentTemp: Float,
    var medizin: String){
    constructor( parentBoxId: Long,currentTemp: Float,medizin: String) : this(0,parentBoxId, currentTemp, medizin)
}

//1:n Box - Kammer
data class BoxWithKammern(
    @Embedded val transportbox: Transportbox,
    @Relation(
        parentColumn = "boxId",
        entityColumn = "parentBoxId",
    )
    val kammern: List<Kammer>
    )



/*

class DummyBox(
     label: String,
     listKammer: List<Kammer>,
     origin : String,
     destination: String,
     hasArrived : Boolean,
     arrival : String =""): Box(0,label,listKammer,origin,destination,hasArrived,arrival)


val dummyMedizinA = Medizin("Dummy Medizin A", 12f)
val dummyMedizinB = Medizin("Dummy Medizin B", 22f)
val dummyMedizinC = Medizin("Dummy Medizin C", 17f)
val dummyMedizinD = Medizin("Dummy Medizin D", 31f)

val dummyKammerA = Kammer(dummyMedizinA, 15f)
val dummyKammerB = Kammer(dummyMedizinB, 25f)
val dummyKammerC = Kammer(dummyMedizinC, 19f)
val dummyKammerD = Kammer(dummyMedizinD, 30f)

val dummyKammerListeA : List<Kammer> = listOf(dummyKammerA,dummyKammerB,dummyKammerC,dummyKammerD)
val dummyKammerListeB : List<Kammer> = listOf(dummyKammerB,dummyKammerB,dummyKammerC,dummyKammerD)
val dummyKammerListeC : List<Kammer> = listOf(dummyKammerC,dummyKammerB,dummyKammerC,dummyKammerD)
val dummyKammerListeD : List<Kammer> = listOf(dummyKammerD,dummyKammerB,dummyKammerC,dummyKammerD)
val dummyKammerListeE : List<Kammer> = listOf(dummyKammerA,dummyKammerB,dummyKammerC,dummyKammerD)

val dummyBoxA = DummyBox("Box ABC", dummyKammerListeA,"Frankfurt", "Gummersbach", true, "03.06.21 - 12:15 Uhr")
val dummyBoxB = DummyBox("Box DEF", dummyKammerListeB,"Gummersbach", "Berlin", false )
val dummyBoxC = DummyBox("Box GHI", dummyKammerListeC,"Koeln", "Hamburg", true, "12.06.21 - 16: 30 Uhr")
val dummyBoxD = DummyBox("Box JKL", dummyKammerListeD,"Muenchen", "Koeln", false)
val dummyBoxE = DummyBox("Box MNO", dummyKammerListeE,"Berlin", "Muenchen ", false)

val dummyList: List<Box> = listOf(dummyBoxA,dummyBoxB,dummyBoxC,dummyBoxD,dummyBoxE)*/
