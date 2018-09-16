package alvin.ui.layout.listing.views

import alvin.ui.layout.R
import alvin.ui.layout.listing.ListingModule
import alvin.ui.layout.listing.domain.models.FileItem
import alvin.ui.layout.listing.views.adapters.ListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_listing_list_view.*
import javax.inject.Inject

class ListViewActivity : DaggerAppCompatActivity() {

    private lateinit var adapter: ListAdapter

    @Inject
    @field:ListingModule.DataList
    lateinit var fileItems: List<FileItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_list_view)

        createListView()
    }

    private fun createListView() {
        val inflater = LayoutInflater.from(this)

        val viewHeader = inflater.inflate(R.layout.view_list_header, list_view, false)
        list_view.addHeaderView(viewHeader)

        val viewFooter = inflater.inflate(R.layout.view_list_footer, list_view, false)
        list_view.addFooterView(viewFooter)

        adapter = ListAdapter(this)
        list_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        adapter.update(fileItems)
    }
}
