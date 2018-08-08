package com.genius.amateur.whatschat.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.genius.amateur.whatschat.R
import com.genius.amateur.whatschat.adapters.SectionPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    var sectionPagerAdapter: SectionPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar!!.title = "Dashboard"

        sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager)
        dshbrd_viewpager.adapter = sectionPagerAdapter
        dshbrd_tab.setupWithViewPager(dshbrd_viewpager)
        dshbrd_tab.setTabTextColors(Color.WHITE, resources.getColor(R.color.colorAccent))

        if (intent.extras != null) {
            var userName = intent.getStringExtra("name")
            Toast.makeText(this, "Welcome $userName", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if (item != null) {
            if (item.itemId == R.id.app_bar_logout) {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            if (item.itemId == R.id.app_bar_setting) {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return true
    }

}
