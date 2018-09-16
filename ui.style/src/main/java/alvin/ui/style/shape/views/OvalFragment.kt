package alvin.ui.style.shape.views

import alvin.ui.style.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class OvalFragment : Fragment() {

    companion object {
        fun newInstance(): OvalFragment {
            return OvalFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shape_oval, container, false)
    }
}
