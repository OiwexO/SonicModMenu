package com.iwex.sonicmodmenu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.iwex.sonicmodmenu.domain.NativeBridge

class GameTabViewModel : ViewModel() {
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

}