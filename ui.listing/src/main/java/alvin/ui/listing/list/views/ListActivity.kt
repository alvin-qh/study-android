package alvin.ui.listing.list.views

import alvin.ui.listing.R
import alvin.ui.listing.domain.DomainModule
import alvin.ui.listing.domain.models.FileItem
import android.os.Bundle
import android.view.LayoutInflater
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.list_activity.*
import javax.inject.Inject
import javax.inject.Named

class ListActivity : DaggerAppCompatActivity() {

    private lateinit var adapter: ListAdapter

    @field:[Inject Named(DomainModule.NAME_DATA_LIST)] lateinit var fileItems: List<FileItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        createListView()
    }

    private fun createListView() {
        val inflater = LayoutInflater.from(this)

        val viewHeader = inflater.inflate(R.layout.list_view_header, list_view, false)
        list_view.addHeaderView(viewHeader)

        val viewFooter = inflater.inflate(R.layout.list_view_footer, list_view, false)
        list_view.addFooterView(viewFooter)

        adapter = ListAdapter(this)
        list_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.update(fileItems)
    }
}
