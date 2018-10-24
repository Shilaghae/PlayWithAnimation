package com.loveview.loveview

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Point
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_love.*


class LoveActivity : AppCompatActivity() {

    private val fabs = Array(4) { FPoint() }
    private var fabButtons = arrayOfNulls<FloatingActionButton>(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love)
        setSupportActionBar(mainToolbar)
        supportActionBar!!.setTitle("Animations")
        play_to_pause.setOnClickListener({
            play_to_pause.drawable.startAsAnimatable()
        })
        initFabButtons()
        initFabs()
        floatingActionButton.setOnClickListener({
            animate()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.actionbar_menu_snowflake -> {
                play_to_pause.visibility = View.GONE
                infinite_snowflake.visibility = View.VISIBLE
            }
            R.id.actionbar_menu_play -> {
                play_to_pause.visibility = View.VISIBLE
                infinite_snowflake.visibility = View.GONE
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun initFabs() {
        val angolo: Double = 90.0 / (fabs.size - 1)
        val raggio = 400
        for (i in 0..fabs.size - 1) {
            fabs[i].x = -(raggio * Math.cos(Math.toRadians(angolo * i)))
            fabs[i].y = -(raggio * Math.sin(Math.toRadians(angolo * i)))
        }
    }

    fun initFabButtons() {
        for (i in 0..fabButtons.size - 1) {
            fabButtons[i] = FloatingActionButton(this)
            val layoutParams = LinearLayout.LayoutParams(5, 5)
            fabButtons[i]!!.layoutParams = layoutParams
            fabButtons[i]!!.x = 0f
            fabButtons[i]!!.y = 0f
            fabButtons[i]!!.visibility = View.VISIBLE
            findViewById<ConstraintLayout>(R.id.activity_love).addView(fabButtons[i])
        }
    }

    fun animate() {
        val animatorSet = AnimatorSet()
        for (i in 0..fabs.size - 1) {
            val animateX: ValueAnimator = ValueAnimator.ofFloat(floatingActionButton.x + floatingActionButton.width / 2, fabs[i].x.toFloat() + floatingActionButton.x)
            animateX.addUpdateListener {
                fabButtons[i]!!.x = (it.animatedValue as Float)
                fabButtons[i]!!.requestLayout()
            }
            animateX.setDuration(300)

            val animateY: ValueAnimator = ValueAnimator.ofFloat(floatingActionButton.y + floatingActionButton.height / 2, fabs[i].y.toFloat() + floatingActionButton.y)
            animateY.addUpdateListener {
                fabButtons[i]!!.y = (it.animatedValue as Float)
                fabButtons[i]!!.requestLayout()
            }
            animateY.setDuration(300)

            val animateSize: ValueAnimator = ValueAnimator.ofInt(5, floatingActionButton.width)
            animateSize.addUpdateListener {
                fabButtons[i]!!.layoutParams.width = it.animatedValue as Int
                fabButtons[i]!!.layoutParams.height = it.animatedValue as Int
            }
            animateSize.setDuration(300)

            animatorSet.play(animateX).with(animateY).with(animateSize)
            animatorSet.start()
        }
    }

    private fun Drawable.startAsAnimatable() = (this as Animatable).start()
}
