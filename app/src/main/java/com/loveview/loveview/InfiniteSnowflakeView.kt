package com.loveview.loveview

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import java.util.*

class InfiniteSnowflakeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val stars = Array(32) { Snowflake() }

    private val drawable: Drawable?

    private val random = Random(1337)

    private val randomSpeed = Random()

    private val base: Float

    private val timeAnnotation: TimeAnimator = TimeAnimator()

    init {
        drawable = ContextCompat.getDrawable(getContext(), R.drawable.snowflake)
        base = Math.max(drawable!!.getIntrinsicWidth(), drawable.getIntrinsicHeight()) / 20f
    }

    private fun initializeStar(snowflake: Snowflake, viewWidth: Int, viewHeight: Int) {
        snowflake.x = viewWidth * random.nextFloat()
        snowflake.y = viewHeight.toFloat() + drawable!!.intrinsicHeight
        snowflake.alpha = random.nextFloat() + snowflake.alpha
        snowflake.speed = randomSpeed.nextInt(500) + 300
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        for(star in stars) {
            initializeStar(star, w, h)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        timeAnnotation.setTimeListener(object : TimeAnimator.TimeListener {
            override fun onTimeUpdate(p0: TimeAnimator?, totalTime: Long, deltaTime: Long) {
                if (!isLaidOut()) {
                    return
                }
                updateState(deltaTime.toFloat())
                invalidate()
            }
        })
        timeAnnotation.start()
    }

    private fun updateState(delta: Float) {
        val deltaSecond = delta / 1000
        for (star in stars) {
            star.y = star.y.minus(star.speed * deltaSecond)

            if (star.y < -drawable!!.intrinsicHeight) {
                initializeStar(star, width, height)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        for (star in stars) {
            val save = canvas.save()
            canvas.translate(star.x, star.y)
            val progress = star.y / height
            canvas.rotate(360 * progress)
            val size = Math.round(base)
            drawable!!.apply {
                setAlpha(Math.round(255 * star.alpha))
                setBounds(-size, -size, size, size)
                draw(canvas)
            }
            canvas.restoreToCount(save)
        }
    }
}