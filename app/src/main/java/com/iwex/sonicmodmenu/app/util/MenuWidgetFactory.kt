package com.iwex.sonicmodmenu.app.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.Gravity
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.setMargins


class MenuWidgetFactory {
    companion object {

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        fun addSwitch(
            value: Boolean,
            checkedListener: (isChecked: Boolean) -> Unit,
            label: String,
            context: Context,
            parent: ViewGroup
        ): Switch {
            return Switch(context).apply {
                isChecked = value
                text = label
                minHeight = MenuDesign.Measurements.SWITCH_HEIGHT
//                if (Build.VERSION.SDK_INT >= 23) {
//                    thumbTintList = ColorStateList.valueOf(MenuDesign.Colors.SWITCH_THUMB)
//                    trackTintList = ColorStateList.valueOf(MenuDesign.Colors.BUTTON_TEXT)
//                }
                setTextColor(MenuDesign.Colors.MAIN)
                parent.addView(this)
                setOnCheckedChangeListener { _, isChecked ->
                    checkedListener(isChecked)
                }
            }
        }

        fun addSeekBar(
            label: String,
            max: Int,
            progress: Int,
            progressListener: (progress: Int) -> Unit,
            context: Context,
            parent: ViewGroup,
            textLabelValues: Array<String>? = null
        ): SeekBar {
            val isTextLabel = textLabelValues != null && textLabelValues.size == max + 1
            val textViewLabel = if (isTextLabel) {
                addSeekBarLabel(label, textLabelValues!![progress], context, parent)
            } else {
                addSeekBarLabel(label, progress, context, parent)
            }
            return SeekBar(context).apply {
                this.max = max
                this.progress = progress
                thumb = ColorDrawable(MenuDesign.Colors.MAIN)
                progressDrawable.colorFilter = PorterDuffColorFilter(
                    MenuDesign.Colors.SEEKBAR_PROGRESS, PorterDuff.Mode.SRC_ATOP
                )
                if (Build.VERSION.SDK_INT >= 29) {
                    minHeight = MenuDesign.Measurements.SEEKBAR_HEIGHT
                }
                parent.addView(this)
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        textViewLabel.text = String.format(
                            label,
                            if (isTextLabel) textLabelValues!![progress] else progress
                        )
                        progressListener(progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar) {
//                        progressListener(seekBar.progress)
                    }
                })
            }
        }

        private fun <T>addSeekBarLabel(
            label: String,
            value: T,
            context: Context,
            parent: ViewGroup
        ): TextView {
            return TextView(context).apply {
                text = String.format(label, value)
                setTextColor(MenuDesign.Colors.MAIN)
                parent.addView(this)
            }
        }

        fun addButton(
            label: String,
            addMargin: Boolean,
            context: Context,
            parent: ViewGroup
        ): Button {
            return Button(context).apply {
                text = label
                if (addMargin) {
                    layoutParams = LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(MenuDesign.Measurements.BUTTON_MARGIN)
                    }
                }
                background = getButtonBackground()
                setTextColor(MenuDesign.Colors.BUTTON_TEXT)
                gravity = Gravity.CENTER
                parent.addView(this)
            }
        }

        private fun getButtonBackground(): GradientDrawable {
            return GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = MenuDesign.Measurements.BUTTON_CORNER_RADIUS
                setColor(MenuDesign.Colors.BUTTON_BACKGROUND)
            }
        }

        fun addTitle(
            label: String,
            context: Context,
            parent: ViewGroup
        ): TextView {
            return TextView(context).apply {
                text = label
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setTextColor(MenuDesign.Colors.MAIN)
                textSize = MenuDesign.Measurements.TITLE_TEXT_SIZE
                parent.addView(this)
            }
        }

        @SuppressLint("SetTextI18n")
        fun addNumberInput(
            label: String,
            maxValue: Int,
            onInputChangedListener: (input: Int) -> Unit,
            context: Context,
            parent: ViewGroup,
            isBinary: Boolean = false
        ): Button {
            val linearLayout = LinearLayout(context)
            val widgetButton = Button(context).apply {
                text = String.format("%s%d", label, 0)
                isAllCaps = false
                layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                ).apply {
                    setMargins(MenuDesign.Measurements.BUTTON_MARGIN)
                }
                background = getButtonBackground()
                setTextColor(MenuDesign.Colors.BUTTON_TEXT)
                setOnClickListener {
                    showNumberInputDialog(
                        context,
                        label,
                        maxValue,
                        { inputNumber ->
                            text = if (isBinary) {
                                String.format("%s%s", label, Integer.toBinaryString(inputNumber))
                            } else {
                                String.format("%s%d", label, inputNumber)
                            }
                            onInputChangedListener(inputNumber)
                        },
                        isBinary
                    )
                }
            }
            linearLayout.addView(widgetButton)
            parent.addView(linearLayout)
            return widgetButton
        }

        private fun showNumberInputDialog(
            context: Context,
            label: String,
            maxValue: Int,
            onInputChangedListener: (input: Int) -> Unit,
            isBinary: Boolean
        ) {
            val editText = createInputDialogContentView(context, maxValue, isBinary)
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Input $label")
            dialogBuilder.setView(editText)
            dialogBuilder.setPositiveButton("OK") { _, _ ->
                val inputText = editText.text.toString()
                var inputNumber = if (isBinary) {
                    try {
                        inputText.toInt(2)
                    } catch (e: NumberFormatException) {
                        0
                    }
                } else {
                    inputText.toIntOrNull() ?: maxValue
                }
                inputNumber = inputNumber.coerceAtMost(maxValue)
                onInputChangedListener(inputNumber)
                editText.isFocusable = false
            }
            dialogBuilder.setNegativeButton("Cancel") { _, _ ->
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
            }
            val dialog: AlertDialog = dialogBuilder.create()
            val windowType =
                if (Build.VERSION.SDK_INT >= 26) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else WindowManager.LayoutParams.TYPE_APPLICATION
            dialog.window?.setType(windowType)
            dialog.show()
            editText.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

        private fun createInputDialogContentView(
            context: Context,
            maxValue: Int,
            isBinary: Boolean
        ): EditText {
            val editText = EditText(context).apply {
                hint = StringBuilder("Max value: ").append(maxValue).toString()
                inputType = InputType.TYPE_CLASS_NUMBER
                keyListener = DigitsKeyListener.getInstance(if (isBinary) "01" else "0123456789")
                filters = arrayOf(LengthFilter(if (isBinary) 7 else 6))
                onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (hasFocus) {
                        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
                    } else {
                        imm.hideSoftInputFromWindow(windowToken, 0)
                    }
                }
            }
            return editText
        }

    }
}