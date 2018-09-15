package alvin.ui.listing.recycler.views

import alvin.ui.listing.R
import alvin.ui.listing.domain.DomainModule
import alvin.ui.listing.domain.models.FileItem
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_recycler.*
import javax.inject.Inject

class RecyclerActivity : DaggerAppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter

    @Inject
    @field:DomainModule.DataList
    lateinit var fileItems: List<FileItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

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
