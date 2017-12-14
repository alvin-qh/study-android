package alvin.ui.listing.list.recyclerview.views

import alvin.lib.mvp.views.AppCompatActivityView
import alvin.ui.listing.R
import alvin.ui.listing.domain.models.FileItem
import alvin.ui.listing.list.recyclerview.RecyclerViewContracts
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_recyclerview_activity.*

class RecyclerViewActivity :
        AppCompatActivityView<RecyclerViewContracts.Presenter>(), RecyclerViewContracts.View {

    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_recyclerview_activity)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        adapter = RecyclerViewAdapter(this)
        recycler_view.adapter = adapter
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.makeListContent()
    }

    override fun showFileItem(fileItems: List<FileItem>) {
        adapter.update(fileItems)
    }
}
