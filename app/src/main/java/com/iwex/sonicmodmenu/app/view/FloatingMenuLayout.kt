package com.iwex.sonicmodmenu.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.os.Build
import android.util.Base64
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.core.view.setMargins
import com.iwex.sonicmodmenu.app.util.MenuDesign
import com.iwex.sonicmodmenu.app.util.MenuWidgetFactory

@SuppressLint("ViewConstructor")
class FloatingMenuLayout(
    context: Context,
    private val gameTabLayout: GameTabLayout,
    private val saveEditorTabLayout: SaveEditorTabLayout,
    private val otherTabLayout: OtherTabLayout
) : RelativeLayout(context) {
    val layoutParams: WindowManager.LayoutParams
    private val floatingIconView: ImageView
    private val floatingMenuLayout: LinearLayout
    private val tabHolderScrollView: ScrollView
    private val tabButtonsLayout = LinearLayout(context)
    private val closeMenuButton: Button

    companion object {
        private const val TAG = "FloatingMenuLayout.kt"
        private const val LABEL_MENU_TITLE = "Sonic mod menu by OiwexO"
        private const val LABEL_GAME_BUTTON = "GAME"
        private const val LABEL_SAVES_BUTTON = "SAVES"
        private const val LABEL_OTHER_BUTTON = "OTHER"
        private const val LABEL_CLOSE_MENU_BUTTON = "CLOSE MENU"

        private val DEFAULT_TAB = TabTypes.GAME
        enum class TabTypes {
            GAME,
            SAVE_EDITOR,
            OTHER
        }
    }

    init {
        floatingIconView = initFloatingIconView(context)
        addView(floatingIconView)
        floatingMenuLayout = initFloatingMenuLayout(context)
        addView(floatingMenuLayout)
        tabHolderScrollView = initTabHolderScrollView(context)
        floatingMenuLayout.addView(tabHolderScrollView)
        setActiveTab(DEFAULT_TAB)
        floatingMenuLayout.addView(tabButtonsLayout)
        addTabButton(TabTypes.GAME, context)
        addTabButton(TabTypes.SAVE_EDITOR, context)
        addTabButton(TabTypes.OTHER, context)
        closeMenuButton = MenuWidgetFactory.addButton(
            LABEL_CLOSE_MENU_BUTTON,
            true,
            context,
            floatingMenuLayout
        )
        closeMenuButton.setOnClickListener {
            setActiveTab(TabTypes.GAME)
            floatingMenuLayout.visibility = GONE
            floatingIconView.visibility = VISIBLE
        }
        val windowType =
            if (Build.VERSION.SDK_INT >= 26) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else WindowManager.LayoutParams.TYPE_APPLICATION
        layoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            MenuDesign.Measurements.MENU_POS_X,
            MenuDesign.Measurements.MENU_POS_Y,
            windowType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initFloatingIconView(context: Context): ImageView {
        val iconSize = MenuDesign.Measurements.ICON_SIZE
        val decoded: ByteArray = Base64.decode(MenuDesign.FLOATING_ICON_BASE64, 0)

        return ImageView(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.height = iconSize
            layoutParams.width = iconSize
            scaleType = ImageView.ScaleType.FIT_XY
            setImageBitmap(BitmapFactory.decodeByteArray(decoded, 0, decoded.size))
        }
    }

    private fun initFloatingMenuLayout(context: Context): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(
                MenuDesign.Measurements.MENU_WIDTH,
                MenuDesign.Measurements.MENU_HEIGHT
            )
            setBackgroundColor(MenuDesign.Colors.MENU_BACKGROUND)
            MenuWidgetFactory.addTitle(LABEL_MENU_TITLE, context, this)
            visibility = GONE
        }
    }

    fun onIconClickListener() {
        floatingIconView.visibility = GONE
        floatingMenuLayout.visibility = VISIBLE
    }

    private fun initTabHolderScrollView(context: Context): ScrollView {
        return ScrollView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            ).apply {
                setMargins(MenuDesign.Measurements.BUTTON_MARGIN)
            }
        }
    }

    private fun setActiveTab(tab: TabTypes) {
        tabHolderScrollView.removeAllViews()
        when (tab) {
            TabTypes.GAME -> tabHolderScrollView.addView(gameTabLayout)
            TabTypes.SAVE_EDITOR -> tabHolderScrollView.addView(saveEditorTabLayout)
            TabTypes.OTHER -> tabHolderScrollView.addView(otherTabLayout)
        }

    }

    private fun addTabButton(tab: TabTypes, context: Context) {
        val label = when (tab) {
            TabTypes.GAME -> LABEL_GAME_BUTTON
            TabTypes.SAVE_EDITOR -> LABEL_SAVES_BUTTON
            TabTypes.OTHER -> LABEL_OTHER_BUTTON
        }
        val marginPx = MenuDesign.Measurements.BUTTON_MARGIN
        MenuWidgetFactory.addButton(label, false, context, tabButtonsLayout).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                setMargins(marginPx, 0, marginPx, 0)
            }
            setOnClickListener { setActiveTab(tab) }
        }
    }

}
