package it.eros.moneymoney.views.dashboard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import it.eros.moneymoney.BaseActivity
import it.eros.moneymoney.BaseViewModel
import it.eros.moneymoney.R
import it.eros.moneymoney.utils.dialog.AddInputsDialog
import it.eros.moneymoney.views.dashboard.fragment.DashboardFragment


class DashboardActivity : BaseActivity() {

    private lateinit var mainCoordinator: CoordinatorLayout
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var fab: FloatingActionButton
    private lateinit var dashboard: ImageButton
    private lateinit var stats: ImageButton
    private lateinit var profile: ImageButton
    private lateinit var settings: ImageButton
    private lateinit var bottomLayout: LinearLayout

    private lateinit var switchCompat: SwitchCompat
    private lateinit var editTextDescription: TextInputEditText
    private lateinit var textDefaultCategory: TextView
    private lateinit var textInputLayoutTextPrimaryCategory: TextInputLayout
    private lateinit var editTextPrimaryCategory: TextInputEditText
    private lateinit var textInputLayoutTextSecondaryCategory: TextInputLayout
    private lateinit var editTextSecondaryCategory: TextInputEditText
    private lateinit var saveInput: MaterialButton
    private lateinit var amount: EditText
    private lateinit var spinnerCurrency: Spinner

    private lateinit var viewModel: DashboardViewModel
    private var refreshSwipe: SwipeRefreshLayout? = null

    private lateinit var fabLayoutParams: CoordinatorLayout.LayoutParams
    private var animSet = AnimatorSet()
    private var isBottomLayoutVisible = false

    override fun onDestroy() {
        super.onDestroy()

        removeObserver()
    }

    //-----------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]


        setContentView(R.layout.activity_dashboard)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentActivity, DashboardFragment())
            .commit()

        initWidget()
        addObservable()

    }

    //-----------------------------------------------------------------------------

    private fun initWidget() {
        mainCoordinator = findViewById(R.id.mainCoordinator)
        bottomAppBar = findViewById(R.id.bottomAppBar)
        fab = findViewById(R.id.fab)
        dashboard = findViewById(R.id.dashboard)
        stats = findViewById(R.id.stats)
        profile = findViewById(R.id.profile)
        settings = findViewById(R.id.settings)
        bottomLayout = findViewById(R.id.bottomLayout)
        editTextDescription = findViewById(R.id.editTextDescription)
        textDefaultCategory = findViewById(R.id.textDefaultCategory)
        textInputLayoutTextPrimaryCategory = findViewById(R.id.textInputLayoutTextPrimaryCategory)
        editTextPrimaryCategory = findViewById(R.id.editTextPrimaryCategory)
        textInputLayoutTextSecondaryCategory = findViewById(R.id.textInputLayoutTextSecondaryCategory)
        editTextSecondaryCategory = findViewById(R.id.editTextSecondaryCategory)
        switchCompat = findViewById(R.id.switchCompat)
        saveInput = findViewById(R.id.saveInput)
        amount = findViewById(R.id.amount)
        spinnerCurrency = findViewById(R.id.spinnerCurrency)

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
            toggleBottomLayout()
        }

        mainCoordinator.setOnClickListener {
            editTextDescription.clearFocus()
            closeKeyBoard(editTextDescription)

            editTextPrimaryCategory.clearFocus()
            closeKeyBoard(editTextPrimaryCategory)

            editTextSecondaryCategory.clearFocus()
            closeKeyBoard(editTextPrimaryCategory)
        }


        //-----------------------------------------------------------------------------
        //------------------------- ADD_INPUTS_INIT
        //-----------------------------------------------------------------------------

        textDefaultCategory.setOnClickListener {
            openDialog()
        }



        editTextDescription.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
                closeKeyBoard(v)
                //viewModel.setDeviceIconNameObservable(v.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }


    //-----------------------------------------------------------------------------

    private fun openDialog() {
        val dialog = AddInputsDialog(viewModel)
        dialog.show(this.supportFragmentManager, TAG)
    }

    //-----------------------------------------------------------------------------

    private fun toggleBottomLayout() {
        val animationDuration = 500L

        // Animazione per nascondere il bottomLayout
        if (isBottomLayoutVisible) {

            animSet.interpolator = AccelerateDecelerateInterpolator()
            animSet.playTogether(
                ObjectAnimator.ofFloat(bottomLayout, "translationY", 0f, bottomLayout.height.toFloat())
            )
            animSet.duration = animationDuration
            animSet.start()

            bottomLayout.animate()
                .translationY(bottomLayout.height.toFloat())
                .setDuration(animationDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        bottomLayout.visibility = View.GONE
                    }
                })


            //Animazione della BottomBar che va verso l'alto
            bottomAppBar.visibility = View.VISIBLE

            bottomAppBar.animate()
                .translationY(0F)
                .setDuration(animationDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                    }
                })

            isBottomLayoutVisible = false
        } else {
            // Animazione per mostrare il bottomLayout
            animSet.interpolator = AccelerateDecelerateInterpolator()
            animSet.playTogether(
                ObjectAnimator.ofFloat(bottomLayout, "translationY", bottomLayout.height.toFloat(), 0f)
            )
            animSet.duration = animationDuration
            animSet.start()

            // Aggiorna il layout_anchor del FloatingActionButton
            fabLayoutParams = fab.layoutParams as CoordinatorLayout.LayoutParams
            fabLayoutParams.anchorId = bottomLayout.id
            fabLayoutParams.anchorGravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            fab.layoutParams = fabLayoutParams

            //Animazione della BottomBar che va verso il basso
            bottomAppBar.animate()
                .translationY(bottomAppBar.height.toFloat())
                .setDuration(animationDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        bottomAppBar.visibility = View.GONE
                    }
                })


            bottomLayout.visibility = View.VISIBLE
            isBottomLayoutVisible = true
        }
    }

    //-----------------------------------------------------------------------------

    private fun closeKeyBoard(v: TextView) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
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

    override fun getBaseViewModel(): BaseViewModel? {
        return viewModel
    }

    //-----------------------------------------------------------------------------

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout? {
        return refreshSwipe
    }

    //-----------------------------------------------------------------------------
    //------------------ OBSERVER
    //-----------------------------------------------------------------------------

    private fun addObservable() {
        observeDescription()
        observeDefaultCategory()
        observePrimayCategory()
        observeSecondaryCategory()
    }

    //-----------------------------------------------------------------------------

    private fun observeDescription() {
        viewModel.descriptionObservable.observe(this) {

        }
    }

    //-----------------------------------------------------------------------------

    private fun observeDefaultCategory() {
        viewModel.defaultCategoryObservable.observe(this) {
            if(it.isEmpty()) {
                textDefaultCategory.visibility = View.GONE
                textInputLayoutTextPrimaryCategory.visibility = View.VISIBLE
                textInputLayoutTextSecondaryCategory.visibility = View.VISIBLE
            } else {
                textDefaultCategory.visibility = View.VISIBLE
                textInputLayoutTextPrimaryCategory.visibility = View.GONE
                textInputLayoutTextSecondaryCategory.visibility = View.GONE
            }
        }
    }

    //-----------------------------------------------------------------------------

    private fun observePrimayCategory() {
        viewModel.primaryCategoryObservable.observe(this) {

        }
    }

    //-----------------------------------------------------------------------------

    private fun observeSecondaryCategory() {
        viewModel.secondaryCategoryObservable.observe(this) {

        }
    }

    //-----------------------------------------------------------------------------

    private fun removeObserver() {
        removeObserveDescription()
        removeObserveDefaultCategory()
        removeObservePrimayCategory()
        removeObserveSecondaryCategory()
    }

    //-----------------------------------------------------------------------------

    private fun removeObserveDescription() {
        viewModel.descriptionObservable.removeObservers(this)
    }

    //-----------------------------------------------------------------------------

    private fun removeObserveDefaultCategory() {
        viewModel.defaultCategoryObservable.removeObservers(this)
    }

    //-----------------------------------------------------------------------------

    private fun removeObservePrimayCategory() {
        viewModel.primaryCategoryObservable.removeObservers(this)
    }

    //-----------------------------------------------------------------------------

    private fun removeObserveSecondaryCategory() {
        viewModel.secondaryCategoryObservable.removeObservers(this)
    }

    //-----------------------------------------------------------------------------

    companion object {
        private var TAG = DashboardActivity::class.java.simpleName
    }
}