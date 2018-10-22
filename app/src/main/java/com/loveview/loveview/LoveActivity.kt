package com.loveview.loveview

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_love.*

class LoveActivity : AppCompatActivity() {

    private val tabs = arrayOf("Play!", "Snowflake")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love)
        setSupportActionBar(mainToolbar)
        supportActionBar!!.setTitle("Animations")
        play_to_pause.setOnClickListener({
            play_to_pause.drawable.startAsAnimatable()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
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

    private fun Drawable.startAsAnimatable() = (this as Animatable).start()
}
