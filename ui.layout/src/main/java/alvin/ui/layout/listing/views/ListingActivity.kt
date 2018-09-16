package alvin.ui.layout.listing.views

import alvin.ui.layout.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_listing.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

class ListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_listing)
        setupActions()
    }

    private fun setupActions() {
        arrayOf(btn_list_view, btn_recycler_view).forEach {
            it.onClick { btn ->
                startActivity(when (btn) {
                    btn_list_view -> intentFor<ListViewActivity>()
                    btn_recycler_view -> intentFor<RecyclerViewActivity>()
                    else -> throw IllegalArgumentException()
                })
            }
        }
    }
}
