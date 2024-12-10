package com.example.crimerek

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        if (savedInstanceState == null) {
            // Create an instance of MyFragment
            val fragment = LoginFragment()

            // Add the fragment to the FrameLayout with id fragment_container
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainActivityFrameLayout, fragment)
                .commit()
        }

    }
}