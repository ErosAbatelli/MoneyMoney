package it.eros.moneymoney.views.dashboard

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.eros.moneymoney.BaseActivity
import it.eros.moneymoney.R
import it.eros.moneymoney.views.dashboard.fragment.DashboardFragment


class DashboardActivity : BaseActivity() {

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var fab: FloatingActionButton
    private lateinit var dashboard: ImageButton
    private lateinit var stats: ImageButton
    private lateinit var profile: ImageButton
    private lateinit var settings: ImageButton
    private lateinit var bottomSheetView: View


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
        fab = findViewById(R.id.fab)
        dashboard = findViewById(R.id.dashboard)
        stats = findViewById(R.id.stats)
        profile = findViewById(R.id.profile)
        settings = findViewById(R.id.settings)

        bottomSheetView = layoutInflater.inflate(R.layout.activity_bottom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)


        dashboard.requestFocus()
        dashboard.setImageWithFocus(R.drawable.ic_home_pressed, R.drawable.ic_home)
        stats.setImageWithFocus(R.drawable.ic_stats, R.drawable.ic_stats)
        profile.setImageWithFocus(R.drawable.ic_profile_pressed, R.drawable.ic_profile)
        settings.setImageWithFocus(R.drawable.ic_settings_pressed, R.drawable.ic_settings)


        dashboard.setOnClickListener {
            changeFragment(DashboardFragment())
        }

        stats.setOnClickListener {
        }

        profile.setOnClickListener {
        }

        settings.setOnClickListener {
        }

        fab.setOnClickListener {
            bottomSheetDialog.show()
        }
    }


    //-----------------------------------------------------------------------------

    private fun ImageButton.setImageWithFocus(focusedImage: Int, unfocusedImage: Int) {
        this.setOnFocusChangeListener { view, hasFocus ->
            this.setImageResource(if (hasFocus) focusedImage else unfocusedImage)
        }
    }

    //-----------------------------------------------------------------------------

    private fun changeFragment(fragmentToChange: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentActivity, fragmentToChange)
            .commit()
    }


    //-----------------------------------------------------------------------------

    companion object {
        private var TAG = DashboardActivity::class.java.simpleName
    }
}