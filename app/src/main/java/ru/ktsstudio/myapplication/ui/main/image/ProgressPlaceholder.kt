package ru.ktsstudio.myapplication.ui.main.image

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import ru.ktsstudio.myapplication.R

class ProgressPlaceholder(context: Context) : androidx.swiperefreshlayout.widget.CircularProgressDrawable(context) {

    init {
        ContextCompat.getColor(context, R.color.colorAccent)
            .let(::init)
        start()
    }

    private fun init(@ColorInt color: Int) {
        setStyle(androidx.swiperefreshlayout.widget.CircularProgressDrawable.LARGE)
        setColorSchemeColors(color)
        strokeWidth = STROKE_WIDTH
        centerRadius = CENTER_RADIUS
    }

    companion object {
        private const val STROKE_WIDTH = 5F
        private const val CENTER_RADIUS = 30F
    }
}
