package com.iwex.sonicmodmenu.app.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.WindowManager
import android.widget.Toast
import com.iwex.sonicmodmenu.app.NativeBridge
import com.iwex.sonicmodmenu.app.util.FloatingMenuTouchListener
import com.iwex.sonicmodmenu.app.view.FloatingMenuLayout
import com.iwex.sonicmodmenu.app.view.GameTabLayout
import com.iwex.sonicmodmenu.app.view.OtherTabLayout
import com.iwex.sonicmodmenu.app.view.SaveEditorTabLayout
import com.iwex.sonicmodmenu.app.viewmodel.MenuViewModel

class ModService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var floatingMenu: FloatingMenuLayout

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show()
        setupNativeBridge()
        setupFloatingMenu()
    }

    private fun setupNativeBridge() {
        NativeBridge.setSaveFilePath("CHANGE_ME")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupFloatingMenu() {
        val menuViewModel = MenuViewModel()
        val gameTabLayout = GameTabLayout(this, menuViewModel)
        val saveEditorTabLayout = SaveEditorTabLayout(this, menuViewModel)
        val otherTabLayout = OtherTabLayout(this)
        floatingMenu = FloatingMenuLayout(this, gameTabLayout, saveEditorTabLayout, otherTabLayout)
        floatingMenu.setOnTouchListener(
            FloatingMenuTouchListener(
                windowManager,
                floatingMenu,
                floatingMenu.layoutParams
            )
        )
        windowManager.addView(floatingMenu, floatingMenu.layoutParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(floatingMenu)
    }

    companion object {
        private const val TOAST_TEXT = "YouTube: @iwex\nEnjoy!"
    }

}