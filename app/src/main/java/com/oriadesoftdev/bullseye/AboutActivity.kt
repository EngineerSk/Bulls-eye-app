package com.oriadesoftdev.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oriadesoftdev.bullseye.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.about_page_title)
        binding.button?.setOnClickListener {
            finish()
        }
    }
}