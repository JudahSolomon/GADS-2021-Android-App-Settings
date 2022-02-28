package com.example.settingspractice

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.settingspractice.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)

        //To get the any value in the SharedPreferences (XML File)
        //Step 1: get reference to the SharedPreference
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val autoReplyTime = sharedPreferences.getString(getString(R.string.key_auto_reply_time), "")
        Log.i("MainActivity", "Auto Reply Time: $autoReplyTime")

        //Public info
        val publicInfo = sharedPreferences.getStringSet(getString(R.string.key_my_public_info), null)
        Log.i("MainActivity", "Public Info: $publicInfo")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Called only 'after' the Preference value has changed memory(XML File)
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        when(key) {
            getString(R.string.key_status) -> {
                val newStatus = sharedPreferences?.getString(key, "")
                Toast.makeText(this, "New Status: $newStatus", Toast.LENGTH_SHORT).show()
            }

            getString(R.string.key_auto_reply) -> {
                val autoReply = sharedPreferences?.getBoolean(key, false)
                if (autoReply!!)
                    Toast.makeText(this, "Auto Reply: ON", Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(this, "Auto Reply: OFF", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

}