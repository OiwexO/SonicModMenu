package com.iwex.sonicmodmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iwex.sonicmodmenu.app.LauncherActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LauncherActivity.launch(this)
    }
}