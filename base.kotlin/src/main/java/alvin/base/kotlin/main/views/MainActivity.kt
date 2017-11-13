package alvin.base.kotlin.main.views

import alvin.base.kotlin.R
import alvin.base.kotlin.butterknife.views.ButterKnifeActivity
import alvin.base.kotlin.dagger.views.DaggerActivity
import alvin.base.kotlin.dbflow.views.DBFlowActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        ButterKnife.bind(this)

        val listener = View.OnClickListener { b ->
            val intent: Intent? = when (b.id) {
                R.id.btn_butter_knife -> Intent(this, ButterKnifeActivity::class.java)
                R.id.btn_dbflow -> Intent(this, DBFlowActivity::class.java)
                R.id.btn_dagger -> Intent(this, DaggerActivity::class.java)
                else -> null
            }
            if (intent != null) {
                startActivity(intent)
            }
        }

        arrayOf(btn_butter_knife, btn_dbflow, btn_dagger).forEach { id ->
            id.setOnClickListener(listener)
        }
    }
}
