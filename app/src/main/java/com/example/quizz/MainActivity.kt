package com.example.quizz

import android.content.Context
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.row_score.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        title = "Home"
        nav_view.setNavigationItemSelectedListener(this)
        showFragment(MainFragment())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_game_menu -> {
                title = "Home"
                showFragment(MainFragment())
            }
            R.id.nav_Highscor -> {
                title = "Highscore"
                val fragment = ScoreFragment()
                fragment.listeScores = getScores()
                showFragment(fragment)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun getScores(): List<Pair<String, String>> {
        val tempList: ArrayList<Pair<String, Int>> = arrayListOf()
        getSharedPreferences("myPrefs", Context.MODE_PRIVATE).apply {
            all.keys.forEach {
                val score = this.getInt(it, 0)
                tempList.add(it to score)
            }
        }
        val listScoreSorted = tempList.sortedByDescending { it.second }.map { it.first to it.second.toString() }
        return listScoreSorted
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
