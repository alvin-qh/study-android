package alvin.ui.listing.main.views

import alvin.ui.listing.R
import alvin.ui.listing.list.views.ListActivity
import alvin.ui.listing.recycler.views.RecyclerActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayOf(btn_list_view, btn_recycler_view).forEach {
            it.onClick { btn ->
                startActivity(when (btn) {
                    btn_list_view -> intentFor<ListActivity>()
                    btn_recycler_view -> intentFor<RecyclerActivity>()
                    else -> throw IllegalArgumentException()
                })
            }
        }
    }
}
