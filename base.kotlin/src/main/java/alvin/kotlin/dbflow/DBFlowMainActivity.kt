package alvin.kotlin.dbflow

import alvin.kotlin.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

class DBFlowMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dbflow_main)

        ButterKnife.bind(this)
    }
}