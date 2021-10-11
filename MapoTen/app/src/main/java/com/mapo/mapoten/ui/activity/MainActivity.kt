package com.mapo.mapoten.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapo.mapoten.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BottomNavigationBar()
    }

    private fun BottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navHostFragment.navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.login_01 ||
                destination.id == R.id.login_02_01 ||
                destination.id == R.id.login_02_02 ||
                destination.id == R.id.login_01_01 ||
                destination.id == R.id.login_01_02 ||
                destination.id == R.id.login_01_03 ||
                destination.id == R.id.employment_Detail_01 ||
                destination.id == R.id.businessAccount_01_04 ||
                destination.id == R.id.businessAccountEmploymentDetail_01 ||
                destination.id == R.id.accountProfileView ||
                destination.id == R.id.account_01_01 ||
                destination.id == R.id.account_01_02 ||
                destination.id == R.id.account_01_03 ||
                destination.id == R.id.businessAccount_01_01 ||
                destination.id == R.id.businessAccount_01_02 ||
                destination.id == R.id.businessAccount_01_03


            ) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}