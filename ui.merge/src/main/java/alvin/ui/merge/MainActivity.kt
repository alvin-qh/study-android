package alvin.ui.merge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
    }
}
