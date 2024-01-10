package ru.netology.customview.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import ru.netology.customview.R
import ru.netology.customview.utils.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes
) {
    private var textSize = AndroidUtils.dp(context, 20).toFloat()
    private var lineWidth = AndroidUtils.dp(context, 5)
    private var colors = emptyList<Int>()
    var realisation: Int = 0

    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView) {
            textSize = getDimension(R.styleable.StatsView_textSize, textSize)
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth.toFloat()).toInt()
            colors = listOf(
                getColor(R.styleable.StatsView_color1, generationRandomColor()),
                getColor(R.styleable.StatsView_color2, generationRandomColor()),
                getColor(R.styleable.StatsView_color3, generationRandomColor()),
                getColor(R.styleable.StatsView_color4, generationRandomColor())
            )
            realisation = getInt(R.styleable.StatsView_realisation, realisation)
        }
    }

    var data: List<Float> = emptyList()
        set(value) {
            field = value
            updateAnim()
        }

    private var progress = 0F
    private var valueAnimator: ValueAnimator? = null
    private var radius = 0F
    private var center = PointF()
    private var oval = RectF()

    private val paint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        strokeWidth = lineWidth.toFloat()
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        textSize = this@StatsView.textSize
        strokeWidth = lineWidth.toFloat()
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        radius = min(w, h) / 2F - lineWidth
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius,
            center.y - radius,
            center.x + radius,
            center.y + radius,
        )
    }

    private fun generationRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()) {
            return
        }
        var startFrom = -90F

        when (realisation) {
            1 -> for ((index, datum) in data.withIndex()) {
                val angle = datum * 360F
                paint.color = colors.getOrElse(index) { generationRandomColor() }
                canvas.drawArc(oval, startFrom, angle * progress, false, paint)
                startFrom += angle
            }

            2 -> for ((index, datum) in data.withIndex()) {
                val angle = datum * 360F
                paint.color = colors.getOrElse(index) { generationRandomColor() }
                canvas.drawArc(oval, startFrom + progress * 360, angle * progress, false, paint)
                startFrom += angle
            }

            else -> for ((index, datum) in data.withIndex()) {
                val angle = datum * 360F
                paint.color = colors.getOrNull(index) ?: generationRandomColor()
                canvas.drawArc(oval, startFrom + 45F, -angle / 2 * progress, false, paint)
                canvas.drawArc(oval, startFrom + 45F, angle / 2 * progress, false, paint)
                startFrom += angle
            }
        }

        canvas.drawText(
            "%.2f%%".format(data.sum() * 100),
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint
        )
    }

    private fun updateAnim() {
        valueAnimator?.let {
            it.removeAllListeners()
            it.cancel()
        }
        progress = 0F

        valueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
            addUpdateListener { anim ->
                progress = anim.animatedValue as Float
                invalidate()
            }
            duration = 2500
            interpolator = LinearInterpolator()
        }.also {
            it.start()
        }
    }
}