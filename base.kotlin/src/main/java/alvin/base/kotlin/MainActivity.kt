package alvin.base.kotlin

import alvin.base.kotlin.butterknife.ButterKnifeMainActivity
import alvin.base.kotlin.dbflow.DBFlowMainActivity
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
            }

            if (intent != null) {
                startActivity(intent)
            }
        }

        this.btn_butter_knife.setOnClickListener(listener)
        this.btn_dbflow.setOnClickListener(listener)
    }
}
