package com.example.settingspractice

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.*
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootkey: String?) {
        setPreferencesFromResource(R.xml.settings, rootkey)

        val dataStore = DataStore()

        //this line will override the default SharedPreference and the DataStore will be used instead
//        preferenceManager.preferenceDataStore = dataStore

        //Accessing preference objects to listen for clicks

        val accSettingsPref = findPreference<Preference>("key_account_settings")
        accSettingsPref?.setOnPreferenceClickListener {

            val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionFragmentSettingsToFragmentAccount()
            navController.navigate(action)

            true
        }

        //Read Preference values in a Fragment

        //Step 1: Get reference to the SharedPreferences (XML File)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        //Step 2: Get the value using 'key'
        //auto reply time
        val autoReplyTime = sharedPreferences.getString("key_auto_reply_time", "Not set")
        val tag = "SettingsFragment"
        Log.i(tag, "Auto Reply Time: $autoReplyTime")

        //auto download
        val autoDownload = sharedPreferences.getBoolean("key_auto_download", false)
        Log.i(tag, "Auto Download: $autoDownload")

        //setting Preference change listener Note: this is called before the change is saved
        val statusPref = findPreference<EditTextPreference>(getString(R.string.key_status))
        statusPref?.setOnPreferenceChangeListener { preference, newValue ->
            val newStatus = newValue as String
            if (newStatus.contains("bad")){
                Toast.makeText(context, "Inappropriate Status. Please maintain community guidelines.", Toast.LENGTH_LONG)
                    .show()
                false //false: reject the new value
            } else {
                true //true: accept the new value
            }
        }

        //dynamically changing Preference Summary
        val notificationPref = findPreference<SwitchPreferenceCompat>(getString(R.string.key_new_msg_notification))
        //using the datastore for the notificationPref
        notificationPref?.preferenceDataStore = dataStore
        notificationPref?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> { switchPref ->
            if (switchPref?.isChecked!!)
                "Status: ON"
            else
                "Status: OFF"
        }
    }

    class DataStore : PreferenceDataStore() {

        override fun getBoolean(key: String?, defValue: Boolean): Boolean {
            Log.i("SettingsFragment", "Get Boolean: $defValue")
            return defValue
        }

        override fun putBoolean(key: String?, value: Boolean) {
            //this is where you store boolean values in your datasource
            Log.i("SettingsFragment", "Put Boolean: $value")
        }
    }
}