package alvin.ui.ime.main.views

import alvin.lib.common.utils.SystemServices
import alvin.lib.mvp.contracts.adapters.ActivityAdapter
import alvin.ui.ime.R
import alvin.ui.ime.main.MainContracts
import android.graphics.RectF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivityAdapter : ActivityAdapter<MainContracts.Presenter>(), MainContracts.IView {

    companion object {
        val AUTO_HIDE_INPUT_METHOD = true
    }

    @Inject lateinit var systemServices: SystemServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initialize()
    }

    private fun initialize() {
        btn_input_confirm.setOnClickListener {
            presenter.textInput(et_input.text.toString())
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (AUTO_HIDE_INPUT_METHOD) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                if (canInputMethodHidden(currentFocus, ev)) {
                    systemServices.inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun canInputMethodHidden(view: View?, event: MotionEvent): Boolean {
        if (view is EditText) {
            val location = intArrayOf(0, 0)
            view.getLocationInWindow(location)

            val windowRect = RectF(location[0].toFloat(), location[1].toFloat(),
                    (location[0] + view.width).toFloat(), (location[1] + view.height).toFloat())

            return !windowRect.contains(event.x, event.y)
        }
        return false
    }

    override fun showInput(text: String) {
        tv_input_result.text = getString(R.string.message_input, text)
    }

    override fun hideInputMethod() {
        if (!AUTO_HIDE_INPUT_METHOD) {
            // Get focus window and hide input method from it
            systemServices.inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}
