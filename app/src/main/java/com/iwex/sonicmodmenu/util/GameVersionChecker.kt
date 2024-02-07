package com.iwex.sonicmodmenu.util

import android.content.Context
import android.util.Log
import com.iwex.sonicmodmenu.NativeBridge

class GameVersionChecker {
    companion object {
        private const val TAG = "GameVersionChecker.kt"
        private const val PACKAGE_DEBUG = "com.iwex.sonicmodmenu"
        private const val PACKAGE_SONIC1 = "com.sega.sonic1px"
        private const val PACKAGE_SONIC2 = "com.sega.sonic2px"
        private const val VERSION_INCORRECT = -1
        private const val VERSION_DEBUG = 0
        private const val VERSION_SONIC1 = 1
        private const val VERSION_SONIC2 = 2
        private const val LOG_INCORRECT_PACKAGE = "Incorrect app's package name! Mod will not work correctly"
        private var GAME_VERSION = VERSION_INCORRECT
        fun initGameVersion(context: Context) {
            GAME_VERSION = when (context.packageName) {
                PACKAGE_DEBUG -> VERSION_DEBUG
                PACKAGE_SONIC1 -> VERSION_SONIC1
                PACKAGE_SONIC2 -> VERSION_SONIC2
                else -> VERSION_INCORRECT
            }
            if (GAME_VERSION == VERSION_INCORRECT) {
                Log.e(TAG, LOG_INCORRECT_PACKAGE)
            }
            NativeBridge.setGameVersion(GAME_VERSION)
        }

        val isDebug: Boolean
            get() = GAME_VERSION == VERSION_DEBUG

        val isSonic1: Boolean
            get() = GAME_VERSION == VERSION_SONIC1

        val isSonic2: Boolean
            get() = GAME_VERSION == VERSION_SONIC2
    }
}