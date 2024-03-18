package com.kavin.forex.currencies.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kavin.forex.currencies.common.Constants
import com.kavin.forex.databinding.ActivitySplashBinding
import com.kavin.forex.currencies.common.isAppFirstLaunchPerf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (isAppFirstLaunchPerf(sharedPref)) {
            sharedPref.edit().putBoolean(Constants.PREF_IS_APP_FIRST_OPEN, false).apply()
        }

        supportActionBar?.hide()

        lifecycleScope.launch {
            delay(DURATION_MS_DELAY)
            withContext(Dispatchers.Main) {
                val intent = Intent(this@SplashActivity, ForexActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    companion object {
        private const val DURATION_MS_DELAY = 3000L
    }
}
