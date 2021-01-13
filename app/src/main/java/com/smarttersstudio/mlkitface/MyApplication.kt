package com.smarttersstudio.mlkitface

import android.app.Application
import com.google.firebase.FirebaseApp

open class MyApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    FirebaseApp.initializeApp(this)
  }

}
