package com.app.musicplayer.presentation.utility

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit

const val PREFERENCE_NAME = "preference"
const val GENRE_PREFERENCE_KEY = "genre"
fun editGenrePreference(context: Context,newPreference: String){
    val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
    with(sharedPreferences.edit()){
        putString(GENRE_PREFERENCE_KEY,newPreference)
        apply()
    }
}

fun readGenrePreference(context: Context): String?{
    val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
    return sharedPreferences.getString(GENRE_PREFERENCE_KEY,null)
}