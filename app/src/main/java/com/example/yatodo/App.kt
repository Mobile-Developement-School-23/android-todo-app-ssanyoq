package com.example.yatodo

import android.app.Application
import android.content.Context
import com.example.yatodo.components.ApplicationComponent

class App: Application() {
    val applicationComponent by lazy { ApplicationComponent(this) }

    companion object {
        /**
         * Shortcut to get [App] instance from any context, e.g. from Activity.
         */
        fun get(context: Context): App = context.applicationContext as App
    }
}
