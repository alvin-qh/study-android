package alvin.ui.layout.listing.views

import alvin.ui.layout.R
import alvin.ui.layout.listing.ListingModule
import alvin.ui.layout.listing.domain.models.FileItem
import alvin.ui.layout.listing.views.adapters.RecyclerAdapter
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_listing_recycler_view.*
import javax.inject.Inject

class RecyclerViewActivity : DaggerAppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter

    @Inject
    @field:ListingModule.DataList
    lateinit var fileItems: List<FileItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_recycler_view)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        adapter = RecyclerAdapter(this)

        recycler_view.adapter = adapter
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(getDrawable(R.drawable.shape_divider_line)!!)

        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()

        adapter.update(fileItems)
    }
}
