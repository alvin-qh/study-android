package alvin.kotlin

import alvin.kotlin.butterknife.ButterKnifeMainActivity
import alvin.kotlin.dbflow.DBFlowMainActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_butter_knife, R.id.btn_dbflow)
    fun onButtonOkClick(b: Button) {
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
}
