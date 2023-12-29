package com.iwex.sonicmodmenu.app

@Suppress("KotlinJniMissingFunction")
class NativeBridge {
    companion object {
        init {
            System.loadLibrary("sonicmodmenu")
        }
        external fun setScore(score: Int)
        external fun setLives(lives: Int)
        external fun setRings(rings: Int)
        external fun setShield(value: Boolean)
        external fun setInvincibility(value: Boolean)
        external fun setSuperForm(value: Boolean)
        external fun readSaveFile(slotIndex: Int): IntArray
        external fun writeSaveFile(slotIndex: Int, saveSlotData: IntArray): Boolean
        external fun setSaveFilePath(saveFilePath: String)
        external fun exitThread()
    }
}