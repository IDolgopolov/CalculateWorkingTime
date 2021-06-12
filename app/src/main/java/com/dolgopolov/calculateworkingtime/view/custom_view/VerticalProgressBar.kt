package com.dolgopolov.calculateworkingtime.view.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.dolgopolov.calculateworkingtime.R

class VerticalProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var progress = DEF_PROGRESS_VALUE
    private var maxProgress = DEF_MAX_PROGRESS

    private var progressHeight = -1f
    private var cornersRadius = -1f

    companion object {
        private const val DEF_PROGRESS_VALUE = 0f
        private const val DEF_MAX_PROGRESS = 100f
        private const val MIN_PROGRESS = 20f
    }

    init {
        val customAttrs = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.VerticalProgressBar,
            0,
            0
        )

        try {
            progress = customAttrs
                .getFloat(
                    R.styleable.VerticalProgressBar_progress,
                    DEF_PROGRESS_VALUE
                )
            maxProgress = customAttrs
                .getFloat(
                    R.styleable.VerticalProgressBar_maxProgress,
                    DEF_MAX_PROGRESS
                )
        } finally {
            customAttrs.recycle()
        }
    }

    private val progressPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.progress_color)
    }
    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.background_progress_color)
    }

    private val clipPath = Path()
    private val pathProgress = Path()


    fun setProgress(progress: Float) {
        this.progress = progress
        calculateProgressView()
    }

    fun setMaxProgress(maxProgress: Float) {
        this.maxProgress = maxProgress
        calculateProgressView()
    }

    private fun calculateProgressView() {
        progressHeight = when {
            (progress == 0f) -> 0f
            (progress < MIN_PROGRESS) -> height * (MIN_PROGRESS/100)
            (progress > maxProgress) -> height * 1f
            else -> height * (progress / maxProgress)
        }
        invalidate()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        calculateProgressView()
        cornersRadius = w / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        clipPath.addRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            cornersRadius,
            cornersRadius,
            Path.Direction.CW
        )
        canvas?.clipPath(clipPath)

        pathProgress.addRoundRect(
            0f,
            height - progressHeight,
            width.toFloat(),
            height.toFloat(),
            cornersRadius,
            cornersRadius,
            Path.Direction.CW
        )
        canvas?.drawPath(pathProgress, progressPaint)

        canvas?.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            backgroundPaint
        )
    }
}