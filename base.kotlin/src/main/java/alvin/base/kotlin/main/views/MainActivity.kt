package alvin.base.kotlin.main.views

import alvin.base.kotlin.R
import alvin.base.kotlin.butterknife.views.ButterKnifeActivity
import alvin.base.kotlin.dagger.views.DaggerActivity
import alvin.base.kotlin.dbflow.views.DBFlowActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayOf(btn_butter_knife, btn_dbflow, btn_dagger).forEach { it ->
            it.setOnClickListener {
                val intent: Intent? = when (it) {
                    btn_butter_knife -> intentFor<ButterKnifeActivity>()
                    btn_dbflow -> intentFor<DBFlowActivity>()
                    btn_dagger -> intentFor<DaggerActivity>()
                    else -> null
                }
                if (intent != null) {
                    startActivity(intent)
                }
            }
        }
    }
}
