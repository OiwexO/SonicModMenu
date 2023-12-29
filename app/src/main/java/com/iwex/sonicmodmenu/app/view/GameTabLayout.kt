package com.iwex.sonicmodmenu.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import androidx.core.view.setPadding
import com.iwex.sonicmodmenu.app.util.MenuDesign
import com.iwex.sonicmodmenu.app.util.MenuWidgetFactory
import com.iwex.sonicmodmenu.app.viewmodel.MenuViewModel

@SuppressLint("UseSwitchCompatOrMaterialCode", "ViewConstructor")
class GameTabLayout(
    context: Context, private val viewModel: MenuViewModel
) : LinearLayout(context) {
    private val scoreInput: EditText
    private val livesInput: EditText
    private val ringsInput: EditText

    private val shieldSwitch: Switch
    private val invincibilitySwitch: Switch
    private val superFormSwitch: Switch

    companion object {
        private const val LABEL_SCORE = "score:"
        private const val LABEL_LIVES = "lives:"
        private const val LABEL_RINGS = "rings:"

        private const val LABEL_ENABLE_SHIELD = "enable shield"
        private const val LABEL_ENABLE_INVINCIBILITY = "enable invincibility"
        private const val LABEL_ENABLE_SUPER_FORM = "enable super form"

        private const val MAX_SCORE = 999_999
        private const val MAX_LIVES = 99
        private const val MAX_RINGS = 999

    }

    init {
        orientation = VERTICAL
        setBackgroundColor(MenuDesign.Colors.TAB_BACKGROUND)
        setPadding(MenuDesign.Measurements.TAB_PADDING)

        scoreInput = MenuWidgetFactory.addIntInputField(
            LABEL_SCORE,
            MAX_SCORE,
            viewModel::onScoreChange,
            context,
            this
        )

        livesInput = MenuWidgetFactory.addIntInputField(
            LABEL_LIVES,
            MAX_LIVES,
            viewModel::onLivesChange,
            context,
            this
        )

        ringsInput = MenuWidgetFactory.addIntInputField(
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
