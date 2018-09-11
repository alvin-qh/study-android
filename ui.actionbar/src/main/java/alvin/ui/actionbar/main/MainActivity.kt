package alvin.ui.actionbar.main

import alvin.ui.actionbar.R
import alvin.ui.actionbar.homeup.ActionHomeAsUpActivity
import alvin.ui.actionbar.simple.SimpleActionBarActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupButtons()
    }

    private fun setupButtons() {
        arrayOf(btn_simple_action, btn_action_home_up).forEach {
            it.setOnClickListener { b ->
                val intent = when (b.id) {
                    btn_simple_action.id -> intentFor<SimpleActionBarActivity>()
                    btn_action_home_up.id -> intentFor<ActionHomeAsUpActivity>()
                    else -> throw IllegalArgumentException()
                }
                startActivity(intent)
            }
        }
    }
}
