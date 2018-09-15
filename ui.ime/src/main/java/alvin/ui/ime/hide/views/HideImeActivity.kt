package alvin.ui.ime.hide.views

import alvin.ui.ime.R
import android.graphics.RectF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.inputMethodManager

class HideImeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hide_ime)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                val focus = currentFocus
                if (focus != null) {
                    if (canInputMethodHidden(focus, ev)) {
                        inputMethodManager.hideSoftInputFromWindow(focus.windowToken, 0)
                    }
                }
                super.dispatchTouchEvent(ev)
            }
            else -> super.dispatchTouchEvent(ev)
        }
    }

    private fun canInputMethodHidden(view: View?, event: MotionEvent): Boolean {
        return if (view is EditText) {
            val location = intArrayOf(0, 0)
            view.getLocationInWindow(location)

            val windowRect = RectF(
                    location[0].toFloat(),
                    location[1].toFloat(),
                    (location[0] + view.width).toFloat(),
                    (location[1] + view.height).toFloat())

            !windowRect.contains(event.x, event.y)
        } else {
            false
        }
    }
}
