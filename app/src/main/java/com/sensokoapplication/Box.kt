package com.sensokoapplication

class Box(val label: String, val listKammer: List<Kammer>, var origin : String, var destination: String) {

    fun getCurrentFacts(kammer : String): List<Pair<String,String>>{

        val kammerInt = when(kammer){
            "A" -> 0
            "B" -> 1
            "C" -> 2
            "D" -> 3
            else -> 1
        }

        val temp: Pair<String,String> = Pair("Temperatur",listKammer[kammerInt].currentTemp.toString())
        val inhalt =  Pair("Inhalt",listKammer[kammerInt].inhalt.name)
        val bestTemp = Pair("Ziel Temperatur", listKammer[kammerInt].inhalt.bestTemp.toString())

        val ergList:List<Pair<String,String>> = listOf(temp,inhalt,bestTemp)
        return ergList
    }
}

class Kammer(val label: String, var inhalt: Medizin, var currentTemp: Float)

class Medizin(val name: String,val bestTemp: Float)