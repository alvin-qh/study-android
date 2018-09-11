package alvin.ui.actionbar.simple

import alvin.ui.actionbar.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class SimpleActionBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_action)

        setupActionbar()
        setupButtons()
    }

    private fun setupActionbar() {
        val bar = supportActionBar
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true)
            bar.setIcon(R.drawable.ic_search)
            bar.setDisplayUseLogoEnabled(true)
        }
    }

    private fun setupButtons() {
        btn_hide_action_bar.setOnClickListener {
            val b = it as Button
            val bar = supportActionBar
            if (bar != null) {
                if (bar.isShowing) {
                    bar.hide()
                    b.setText(R.string.btn_show_actionbar)
                } else {
                    bar.show()
                    b.setText(R.string.btn_hide_actionbar)
                }
            }
        }
    }
}
