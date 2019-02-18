package com.andela.logfooddiary

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.andela.logfooddiary.ui.FoodDiary.DisplayLogFragment
import com.andela.logfooddiary.ui.home.HomeFragment
import com.andela.logfooddiary.ui.photoUpload.PhotoUploadFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var transaction: FragmentTransaction

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()

        transaction = supportFragmentManager
                .beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment.newInstance())
        transaction.commit()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                transaction = supportFragmentManager
                        .beginTransaction()
                transaction.replace(R.id.fragment_container, HomeFragment.newInstance())
            }
            R.id.food_diary -> {
                transaction = supportFragmentManager
                        .beginTransaction()
                transaction.replace(R.id.fragment_container, DisplayLogFragment.newInstance())
            }
            R.id.photo_upload -> {
                transaction = supportFragmentManager
                        .beginTransaction()
                transaction.replace(R.id.fragment_container, PhotoUploadFragment.newInstance())
            }
        }
        transaction.commit()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}

