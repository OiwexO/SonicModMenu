package com.iwex.sonicmodmenu.app.viewmodel

import com.iwex.sonicmodmenu.app.NativeBridge

class MenuViewModel {
    fun onScoreChange(score: Int) {
        NativeBridge.setScore(score)
    }

    fun onLivesChange(lives: Int) {
        NativeBridge.setLives(lives)
    }

    fun onRingsChange(rings: Int) {
        NativeBridge.setRings(rings)
    }

    fun onShieldChange(isChecked: Boolean) {
        NativeBridge.setShield(isChecked)
    }

    fun onInvincibilityChange(isChecked: Boolean) {
        NativeBridge.setInvincibility(isChecked)
    }

    fun onSuperFormChange(isChecked: Boolean) {
        NativeBridge.setSuperForm(isChecked)
    }

    fun onSaveSlotChange(slotIndex: Int) {
        NativeBridge.readSaveFile(slotIndex)
    }
}