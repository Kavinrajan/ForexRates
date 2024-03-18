package com.kavin.forex

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ForexRatesApp : Application() {

    /*@Inject
    lateinit var sharedPrefs: SharedPreferences*/

    override fun onCreate() {
        super.onCreate()

       // val nightMode = sharedPrefs.getBoolean(Constants.PREF_THEME_KEY, false)
     //   setAppTheme(nightMode)
    }

    private fun setAppTheme(nightMode: Boolean) {
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
