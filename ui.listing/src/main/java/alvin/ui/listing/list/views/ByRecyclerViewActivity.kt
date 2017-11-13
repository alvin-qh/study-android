package alvin.ui.listing.list.views

import alvin.ui.listing.R
import alvin.ui.listing.list.domain.models.FileItem
import alvin.ui.listing.list.domain.models.FileType
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.list_activity_by_recycler_view.*
import kotlinx.android.synthetic.main.list_view_file_item.view.*

class ByRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity_by_recycler_view)

        ButterKnife.bind(this)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        val adapter = RecyclerViewAdapter(this, listOf(
                FileItem(1L, FileType.FILE, "README.md"),
                FileItem(2L, FileType.DIRECTORY, "Project"),
                FileItem(3L, FileType.FILE, "Main.cpp"),
                FileItem(4L, FileType.FILE, "Main.hpp"),
                FileItem(5L, FileType.FILE, "README.md"),
                FileItem(6L, FileType.DIRECTORY, "Project"),
                FileItem(7L, FileType.FILE, "Main.cpp"),
                FileItem(8L, FileType.FILE, "Main.hpp"),
                FileItem(9L, FileType.FILE, "README.md"),
                FileItem(10L, FileType.DIRECTORY, "Project"),
                FileItem(11L, FileType.FILE, "Main.cpp"),
                FileItem(12L, FileType.FILE, "Main.hpp"),
                FileItem(13L, FileType.FILE, "README.md"),
                FileItem(14L, FileType.DIRECTORY, "Project"),
                FileItem(15L, FileType.FILE, "Main.cpp"),
                FileItem(16L, FileType.FILE, "Main.hpp")
        ))
        recycler_view.adapter = adapter
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.update()
    }
}

private class RecyclerViewAdapter
constructor(
        context: Context,
        private var fileItems: List<FileItem>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    companion object {
        val HEADER = 1
        val ITEM = 2
        val FOOTER = 3
    }

    private val inflater = LayoutInflater.from(context)

    fun update(fileItems: List<FileItem>? = null) {
        if (fileItems != null) {
            this.fileItems = fileItems
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.viewType) {
            HEADER -> {
            }
            ITEM -> {
                if (holder is ViewItemHolder) {
                    val fileItem = fileItems[position - 1]

                    holder.ivLogo.setImageResource(when (fileItem.type) {
                        FileType.FILE -> R.drawable.ic_file
                        else -> R.drawable.ic_folder
                    })
                    holder.tvFilename.text = fileItem.name
                }
            }
            FOOTER -> {
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            fileItems.size + 1 -> FOOTER
            else -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewAdapter.ViewHolder {
        return when (viewType) {
            HEADER -> ViewHeaderHolder(inflater.inflate(R.layout.list_view_list_header, parent, false))
            FOOTER -> ViewFooterHolder(inflater.inflate(R.layout.list_view_list_footer, parent, false))
            ITEM -> ViewItemHolder(inflater.inflate(R.layout.list_view_file_item, parent, false))
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun getItemCount(): Int {
        return fileItems.size + 2   // SUPPRESS
    }

    open class ViewHolder
    constructor(view: View, val viewType: Int) : RecyclerView.ViewHolder(view)

    class ViewHeaderHolder
    constructor(view: View) : ViewHolder(view, HEADER)

    class ViewItemHolder
    constructor(view: View) : ViewHolder(view, ITEM) {
        val ivLogo = view.iv_logo!!
        val tvFilename = view.tv_file_name!!
    }

    class ViewFooterHolder
    constructor(view: View) : ViewHolder(view, FOOTER)
}
