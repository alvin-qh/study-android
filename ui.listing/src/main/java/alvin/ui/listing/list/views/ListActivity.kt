package alvin.ui.listing.list.views

import alvin.ui.listing.R
import alvin.ui.listing.list.listview.views.ListViewActivity
import alvin.ui.listing.list.recyclerview.views.RecyclerViewActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import butterknife.ButterKnife
import butterknife.OnClick

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_list_view, R.id.btn_recycler_view)
    fun onButtonsClick(b: Button) {

        val intent = when (b.id) {
            R.id.btn_list_view -> Intent(this, ListViewActivity::class.java)
            R.id.btn_recycler_view -> Intent(this, RecyclerViewActivity::class.java)
            else -> null
        }

        if (intent != null) {
            startActivity(intent)
        }
    }
}
