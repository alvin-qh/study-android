package alvin.ui.layout.main.views

import alvin.ui.layout.R
import alvin.ui.layout.listing.views.ListingActivity
import alvin.ui.layout.merge.views.MergeActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupActions()
    }

    private fun setupActions() {
        arrayOf(btn_listing,
                btn_merge).forEach {
            it.onClick { btn ->
                startActivity(when (btn) {
                    btn_listing -> intentFor<ListingActivity>()
                    btn_merge -> intentFor<MergeActivity>()
                    else -> throw IllegalArgumentException()
                })
            }
        }
    }
}
