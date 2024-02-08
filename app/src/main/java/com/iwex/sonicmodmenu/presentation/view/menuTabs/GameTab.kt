package com.iwex.sonicmodmenu.presentation.view.menuTabs

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import android.widget.Switch
import com.iwex.sonicmodmenu.presentation.viewmodel.GameTabViewModel
import com.iwex.sonicmodmenu.presentation.MenuWidgetFactory

@SuppressLint("UseSwitchCompatOrMaterialCode", "ViewConstructor")
class GameTab(
    context: Context, private val viewModel: GameTabViewModel
) : BaseMenuTab(context) {
    private val scoreInput: Button
    private val livesInput: Button
    private val ringsInput: Button

    private val shieldSwitch: Switch
    private val invincibilitySwitch: Switch
    private val superFormSwitch: Switch

    companion object {
        private const val LABEL_SCORE = "score"
        private const val LABEL_LIVES = "lives"
        private const val LABEL_RINGS = "rings"

        private const val LABEL_ENABLE_SHIELD = "enable shield"
        private const val LABEL_ENABLE_INVINCIBILITY = "enable invincibility"
        private const val LABEL_ENABLE_SUPER_FORM = "enable super form"

        private const val MAX_SCORE = 999_999
        private const val MAX_LIVES = 99
        private const val MAX_RINGS = 999

    }

    init {
        scoreInput = MenuWidgetFactory.addNumberInput(
            LABEL_SCORE,
            MAX_SCORE,
            viewModel::onScoreChange,
            context,
            this
        )

        livesInput = MenuWidgetFactory.addNumberInput(
            LABEL_LIVES,
            MAX_LIVES,
            viewModel::onLivesChange,
            context,
            this
        )

        ringsInput = MenuWidgetFactory.addNumberInput(
            LABEL_RINGS,
            MAX_RINGS,
            viewModel::onRingsChange,
            context,
            this
        )

        shieldSwitch = MenuWidgetFactory.addSwitch(
            false,
            viewModel::onShieldChange,
            LABEL_ENABLE_SHIELD,
            context,
            this
        )

        invincibilitySwitch = MenuWidgetFactory.addSwitch(
            false,
            viewModel::onInvincibilityChange,
            LABEL_ENABLE_INVINCIBILITY,
            context,
            this
        )

        superFormSwitch = MenuWidgetFactory.addSwitch(
            false,
            viewModel::onSuperFormChange,
            LABEL_ENABLE_SUPER_FORM,
            context,
            this
        )

    }

}
