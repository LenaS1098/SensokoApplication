package com.sensokoapplication.ui.navigation

import androidx.annotation.StringRes
import com.sensokoapplication.R

sealed class Screen(val route: String, @StringRes val resourceId : Int, val iconId : Int ){
    object Box : Screen("box", R.string.box, R.drawable.ic_box)
    object Scanner : Screen("scanner", R.string.scanner,R.drawable.ic__qr_code_scanner)
    object Favorites : Screen("favorites",R.string.favorites, R.drawable.ic_star)
}
