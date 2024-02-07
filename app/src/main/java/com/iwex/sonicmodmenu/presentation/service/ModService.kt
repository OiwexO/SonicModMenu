package com.iwex.sonicmodmenu.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.WindowManager
import android.widget.Toast
import com.iwex.sonicmodmenu.presentation.view.FloatingMenu
import com.iwex.sonicmodmenu.presentation.view.GameTab
import com.iwex.sonicmodmenu.presentation.view.OtherTab
import com.iwex.sonicmodmenu.presentation.view.SaveEditorTab
import com.iwex.sonicmodmenu.presentation.viewmodel.GameTabViewModel
import com.iwex.sonicmodmenu.presentation.viewmodel.SaveEditorTabViewModel
import com.iwex.sonicmodmenu.util.FloatingMenuTouchListener

class ModService : Service() {

    private val windowManager: WindowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }

    private var floatingMenu: FloatingMenu? = null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show()
        initFloatingMenu()
    }

    private fun initFloatingMenu() {
        val gameTabViewModel = GameTabViewModel()
        val saveEditorTabViewModel = SaveEditorTabViewModel()
        val gameTab = GameTab(this, gameTabViewModel)
        val saveEditorTab = SaveEditorTab(this, saveEditorTabViewModel)
        val otherTab = OtherTab(this)
        val floatingMenu = FloatingMenu(this, gameTab, saveEditorTab, otherTab)
        floatingMenu.setOnTouchListener(
            FloatingMenuTouchListener(
                floatingMenu.layoutParams
            ) { view, layoutParams -> windowManager.updateViewLayout(view, layoutParams) }
        )
        windowManager.addView(floatingMenu, floatingMenu.layoutParams)
        this.floatingMenu = floatingMenu
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingMenu?.let {
            windowManager.removeView(it)
        }

    }

    companion object {
        private const val TOAST_TEXT = "YouTube: @iwex\nEnjoy!"
    }

}