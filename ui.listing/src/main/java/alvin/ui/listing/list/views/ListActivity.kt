package alvin.ui.listing.list.views

import alvin.ui.listing.R
import alvin.ui.listing.domain.DomainModule
import alvin.ui.listing.domain.models.FileItem
import android.os.Bundle
import android.view.LayoutInflater
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : DaggerAppCompatActivity() {

    private lateinit var adapter: ListAdapter

    @Inject
    @field:DomainModule.DataList
    lateinit var fileItems: List<FileItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

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
