package it.eros.moneymoney.utils.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import it.eros.moneymoney.R
import it.eros.moneymoney.views.dashboard.DashboardViewModel

class AddInputsDialog(
    private val viewModel: DashboardViewModel
) : DialogFragment() {

    private lateinit var view: View
    private lateinit var editTextSearch : SearchView
    private lateinit var list : ListView

    private lateinit var listToSee: List<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_inputs)

        // set dialog dimensions
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.8).toInt()
        val height = (metrics.heightPixels * 0.8).toInt()
        dialog.window?.setLayout(width, height)

        return dialog
    }

    //-----------------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view = inflater.inflate(R.layout.dialog_add_inputs, container, false)

        initWidget(view)
        addObservable()


        return view
    }

    //-----------------------------------------------------------------------------

    private fun initWidget(view: View) {
        editTextSearch = view.findViewById(R.id.editTextSearch)
        list = view.findViewById(R.id.list)


    }


    //-----------------------------------------------------------------------------
    //------------------ OBSERVER
    //-----------------------------------------------------------------------------

    private fun addObservable() {
        observeDefaultCategory()
        observePrimayCategory()
        observeSecondaryCategory()
    }

    //-----------------------------------------------------------------------------

    private fun observeDefaultCategory() {
        viewModel.defaultCategoryObservable.observe(this) {

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
        removeObserveDefaultCategory()
        removeObservePrimayCategory()
        removeObserveSecondaryCategory()
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
        private val TAG = AddInputsDialog::class.java.simpleName
    }
}