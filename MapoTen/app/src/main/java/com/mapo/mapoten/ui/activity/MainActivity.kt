package com.mapo.mapoten.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BottomNavigationBar()
    }

    private fun BottomNavigationBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navHostFragment.navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.login_01 ||
                destination.id == R.id.login_02 ||
                destination.id == R.id.login_01_01 ||
                destination.id == R.id.login_01_02) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
//            if (destination.id == R.id.employment_Detail_01) {
//                binding.mainToolBar.visibility = View.GONE
//            } else {
//                binding.mainToolBar.visibility = View.VISIBLE
//            }
        }
    }
}