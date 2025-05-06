package com.bridgit.bridgitsokogarden

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    private lateinit var notificationSwitch: Switch
    private lateinit var usernameEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var themeSpinner: Spinner
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)

        // UI Elements
        notificationSwitch = findViewById(R.id.switch_notifications)
        usernameEditText = findViewById(R.id.edit_username)
        saveButton = findViewById(R.id.btn_save)
        themeSpinner = findViewById(R.id.spinner_theme)

        // Load settings
        notificationSwitch.isChecked = preferences.getBoolean("notifications", true)
        usernameEditText.setText(preferences.getString("username", ""))

        // Set up theme spinner
        val themes = arrayOf("System Default", "Light", "Dark")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = adapter

        // Set current spinner selection based on saved preference
        val currentTheme = preferences.getString("app_theme", "system")
        val selectedIndex = when (currentTheme) {
            "light" -> 1
            "dark" -> 2
            else -> 0
        }
        themeSpinner.setSelection(selectedIndex)

        // Save button logic
        saveButton.setOnClickListener {
            val selectedTheme = when (themeSpinner.selectedItemPosition) {
                1 -> "light"
                2 -> "dark"
                else -> "system"
            }

            val editor = preferences.edit()
            editor.putBoolean("notifications", notificationSwitch.isChecked)
            editor.putString("username", usernameEditText.text.toString())
            editor.putString("app_theme", selectedTheme)
            editor.apply()

            applyTheme(selectedTheme)

            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun applyTheme(theme: String) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        // Recreate app to apply theme
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
