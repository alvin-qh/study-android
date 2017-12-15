package alvin.ui.listing.recycler.views

import alvin.ui.listing.R
import alvin.ui.listing.domain.DomainModule
import alvin.ui.listing.domain.models.FileItem
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.recycler_activity.*
import javax.inject.Inject
import javax.inject.Named

class RecyclerActivity : DaggerAppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter

    @field:[Inject Named(DomainModule.NAME_DATA_LIST)] lateinit var fileItems: List<FileItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_activity)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        adapter = RecyclerAdapter(this)
        recycler_view.adapter = adapter
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()
        adapter.update(fileItems)
    }
}
