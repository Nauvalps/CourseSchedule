package com.dicoding.courseschedule.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var dailyReminder: DailyReminder
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        PreferenceManager.getDefaultSharedPreferences(requireContext())
                .registerOnSharedPreferenceChangeListener(this)
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        dailyReminder = DailyReminder()
        sharedPreferences = requireContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        setSwitch()
        val switchReminder = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        switchReminder?.setOnPreferenceChangeListener { preference, newValue ->
            val isChecked = newValue as? Boolean ?: false
            if (isChecked) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            saveChangeStateSwitch(isChecked)
            true
        }
    }

    private fun saveChangeStateSwitch(checked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(STATUS_SWITCH, checked)
        editor.apply()
    }

    private fun setSwitch() {
        val switchReminder = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        switchReminder?.isChecked = sharedPreferences.getBoolean(STATUS_SWITCH, false)
    }


    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val darkMode = getString(R.string.pref_key_dark)
        key?.let {
            if (it == darkMode) sharedPreferences?.let { pref ->
                val darkModeValues = resources.getStringArray(R.array.dark_mode_value)
                when(pref.getString(darkMode, darkModeValues[0])) {
                    darkModeValues[0] -> updateTheme(NightMode.AUTO.value)
                    darkModeValues[1] -> updateTheme(NightMode.ON.value)
                    darkModeValues[2] -> updateTheme(NightMode.OFF.value)
                    else -> updateTheme(NightMode.AUTO.value)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {
        const val PREFERENCES_NAME = "SettingPreferences"
        private const val STATUS_SWITCH = "status_switch"
    }
}