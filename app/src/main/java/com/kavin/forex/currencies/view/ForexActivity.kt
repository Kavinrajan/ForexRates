package com.kavin.forex.currencies.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kavin.forex.databinding.ActivityForexBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForexActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForexBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setSupportActionBar(binding.toolbar)
    }

    override fun onResume() {
        super.onResume()
          //  navController.navigate(R.id.homeFragment)

    }

}
