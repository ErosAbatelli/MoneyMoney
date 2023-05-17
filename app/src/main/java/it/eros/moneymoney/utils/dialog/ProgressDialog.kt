package it.eros.moneymoney.utils.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import it.eros.moneymoney.R

class ProgressDialog : DialogFragment() {

    var message: String? = null
        set(value) {
            field = value
            if (messageTv != null) {
                messageTv?.text = message
            }
        }
    var messageTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            message = requireArguments().getString("message", "")
            if (requireArguments().containsKey("style")) {
                setStyle(STYLE_NORMAL, requireArguments().getInt("style"))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageTv = view.findViewById(R.id.message)
        messageTv?.text = message
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun newInstance(message: String?, style: Int? = null): ProgressDialog {
            val fragment = ProgressDialog()
            val args = Bundle()
            args.putString("message", message)
            if (style != null) args.putInt("style", style)
            fragment.arguments = args
            return fragment
        }
    }
}