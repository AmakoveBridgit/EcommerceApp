package com.bridgit.bridgitsokogarden

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        when (prefs.getString("theme", "light")) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, right, 0)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
//        fetch by ids

        val signup=findViewById<Button>(R.id.signup)
        val signin=findViewById<Button>(R.id.signin)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)

        val progressbar=findViewById<ProgressBar>(R.id.progressbar)
        val recyclerview=findViewById<RecyclerView>(R.id.recyclerView)



        val helper = ApiHelper(applicationContext)
        val url = "https://caydenmayavi.pythonanywhere.com/api/get_products_details"


        helper.loadProducts(url, recyclerview, progressbar) { productList ->

            recyclerview.layoutManager = GridLayoutManager(this,2)
            productAdapter = ProductAdapter(productList)
            recyclerview.adapter = productAdapter

            // Now that adapter is initialized, listen to text changes for filtering
            searchEditText.addTextChangedListener { text ->
                productAdapter.filter(text.toString()) // Apply filter based on search query
            }
        }

        signup.setOnClickListener {
            val signup_intent=Intent(applicationContext,SignupActivity::class.java)
            startActivity(signup_intent)

        }
        signin.setOnClickListener {
            val signin_intent=Intent(applicationContext,SigninActivity::class.java)
            startActivity(signin_intent)

        }

        val about = findViewById<Button>(R.id.about)
        about.setOnClickListener {
            val intent = Intent(applicationContext, About::class.java)
            startActivity(intent)
        }

        val speech = findViewById<Button>(R.id.btnSpeak)
        speech.setOnClickListener {
            val intent = Intent(applicationContext, SpeechToText::class.java)
            startActivity(intent)
        }

//        val navController = findNavController(R.id.nav_host_fragment)
//        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNavigation.setupWithNavController(navController)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already in MainActivity
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                else -> false
            }
        }

        }

    }
