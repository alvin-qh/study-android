package alvin.base.kotlin

import alvin.base.kotlin.butterknife.ButterKnifeMainActivity
import alvin.base.kotlin.dagger.views.DaggerMainActivity
import alvin.base.kotlin.dbflow.views.DBFlowMainActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        val listener = View.OnClickListener { b ->
            var intent: Intent? = null

            when (b.id) {
                R.id.btn_butter_knife -> {
                    intent = Intent(this, ButterKnifeMainActivity::class.java)
                }
                R.id.btn_dbflow -> {
                    intent = Intent(this, DBFlowMainActivity::class.java)
                }
                R.id.btn_dagger -> {
                    intent = Intent(this, DaggerMainActivity::class.java)
                }
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
