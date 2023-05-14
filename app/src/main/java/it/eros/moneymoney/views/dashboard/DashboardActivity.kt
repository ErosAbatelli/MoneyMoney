package it.eros.moneymoney.views.dashboard

import android.os.Bundle
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.eros.moneymoney.BaseActivity
import it.eros.moneymoney.R
import it.eros.moneymoney.views.dashboard.fragment.DashboardFragment

class DashboardActivity : BaseActivity() {

    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentActivity, DashboardFragment())
            .commit()

        initWidget()

    }

    //-----------------------------------------------------------------------------

    private fun initWidget() {
        bottomAppBar = findViewById(R.id.bottomAppBar)

    }



    //-----------------------------------------------------------------------------

    companion object {
        private var TAG = DashboardActivity::class.java.simpleName
    }
}