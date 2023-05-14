package it.eros.moneymoney.views.dashboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.eros.moneymoney.R
import it.eros.moneymoney.views.dashboard.DashboardActivity


class DashboardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //-----------------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    //-----------------------------------------------------------------------------

    companion object {
        private var TAG = DashboardFragment::class.java.simpleName

    }
}