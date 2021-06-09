package com.example.dialogfragmentfun

import android.app.Application
import android.util.Log

const val LOG_TAG = "DialogFragmentFun"

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "Application onCreate")
    }
}