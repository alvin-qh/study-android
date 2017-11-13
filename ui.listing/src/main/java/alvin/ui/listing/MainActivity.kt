package alvin.ui.listing

import alvin.ui.listing.list.views.ListMainActivity
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

    @OnClick(R.id.btn_list, R.id.btn_grid)
    fun onButtonClick(b: Button) {
        val intent: Intent? = when (b.id) {
            R.id.btn_list -> Intent(this, ListMainActivity::class.java)
            else -> null
        }

        if (intent != null) {
            startActivity(intent)
        }
    }
}
