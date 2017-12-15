package alvin.ui.listing.main.views

import alvin.ui.listing.R
import alvin.ui.listing.list.views.ListActivity
import alvin.ui.listing.recycler.views.RecyclerActivity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        arrayOf(btn_list_view, btn_recycler_view).forEach {
            it.onClick {
                val intent: Intent? = when (it) {
                    btn_list_view -> Intent(this@MainActivity, ListActivity::class.java)
                    btn_recycler_view -> Intent(this@MainActivity, RecyclerActivity::class.java)
                    else -> null
                }

                if (intent != null) {
                    startActivity(intent)
                }
            }
        }
    }
}
