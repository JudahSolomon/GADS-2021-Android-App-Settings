package com.example.settingspractice

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.preference.*

class AccountSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        //Step 1: create the individual Preference objects
        val privacyCategory = PreferenceCategory(context)
        val publicInfoPref = MultiSelectListPreference(context)
        val securityCategory = PreferenceCategory(context)
        val logoutPref = Preference(context)
        val deleteMyAccPref = Preference(context)
        //creating PreferenceScreen is different here
        val prefScreen = preferenceManager.createPreferenceScreen(context)

        //define their properties (attributes)

        with(privacyCategory) {
            title = "Privacy"
            isIconSpaceReserved = false
        }
        with(securityCategory) {
            title = "Security"
            isIconSpaceReserved = false
        }

        with(publicInfoPref) {
            key = getString(R.string.key_my_public_info)
            title = getString(R.string.my_public_info)
            isIconSpaceReserved = false
            entries = resources.getStringArray(R.array.entries_my_public_info)
            entryValues = resources.getStringArray(R.array.values_my_public_info)
        }
        with(logoutPref) {
            key = getString(R.string.key_logout)
            title = getString(R.string.logout)
            isIconSpaceReserved = false
        }
        with(deleteMyAccPref) {
            key = getString(R.string.key_delete_my_account)
            title = getString(R.string.delete_my_account)
            icon = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_menu_delete, null)
            isIconSpaceReserved = false
        }

        //Step 2: add all Preference objects in hierarchy
        with(prefScreen) {
            addPreference(privacyCategory)
            addPreference(securityCategory)
        }

        privacyCategory.addPreference(publicInfoPref)

        with(securityCategory) {
            addPreference(logoutPref)
            addPreference(deleteMyAccPref)
        }

        //step 3: Set the preference screen
        preferenceScreen = prefScreen

    }

}