package com.app.musicplayer
import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.LocalCacheSettings
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application : Application(){
    override fun onCreate() {
        super.onCreate()
        val db = Firebase.firestore
        val settings = FirebaseFirestoreSettings.Builder()
            .build()
        db.firestoreSettings = settings
        FirebaseApp.initializeApp(applicationContext)
    }
}