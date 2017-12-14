package alvin.ui.listing.list.listview.views

import alvin.lib.mvp.views.AppCompatActivityView
import alvin.ui.listing.R
import alvin.ui.listing.domain.models.FileItem
import alvin.ui.listing.list.listview.ListViewContracts
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.list_listview_activity.*

class ListViewActivity :
        AppCompatActivityView<ListViewContracts.Presenter>(), ListViewContracts.View {

    lateinit var adapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_listview_activity)

        createListView()
    }

    private fun createListView() {
        val inflater = LayoutInflater.from(this)

        val viewHeader = inflater.inflate(R.layout.list_view_header, list_view, false)
        list_view.addHeaderView(viewHeader)

        val viewFooter = inflater.inflate(R.layout.list_view_footer, list_view, false)
        list_view.addFooterView(viewFooter)

        adapter = ListViewAdapter(this)
        list_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.makeListContent()
    }

    override fun showFileItem(fileItems: List<FileItem>) {
        adapter.update(fileItems)
    }
}
