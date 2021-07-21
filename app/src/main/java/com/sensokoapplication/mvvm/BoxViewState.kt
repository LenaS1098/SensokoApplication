package com.sensokoapplication.mvvm

import com.sensokoapplication.BoxWithKammern
import com.sensokoapplication.Kammer
import com.sensokoapplication.Transportbox



data class BoxViewState(
    var currentTransportbox: Transportbox = Transportbox(-1,"Dummy","GM","K",false),
    var allTransportboxes: List<Transportbox> = emptyList(), var allKammer : List<BoxWithKammern> = emptyList())
