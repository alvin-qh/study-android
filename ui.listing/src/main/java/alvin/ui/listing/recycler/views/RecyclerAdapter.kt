package alvin.ui.listing.recycler.views

import alvin.ui.listing.R
import alvin.ui.listing.domain.models.FileItem
import alvin.ui.listing.domain.models.FileType
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_view_fileitem.view.*

class RecyclerAdapter
constructor(context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    companion object {
        const val HEADER = 1
        const val ITEM = 2
        const val FOOTER = 3
    }

    private val inflater = LayoutInflater.from(context)
    private var fileItems: List<FileItem> = emptyList()

    fun update(fileItems: List<FileItem>) {
        this.fileItems = fileItems
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            HEADER -> ViewHeaderHolder(inflater.inflate(R.layout.list_view_header, parent, false))
            FOOTER -> ViewFooterHolder(inflater.inflate(R.layout.list_view_footer, parent, false))
            ITEM -> ViewItemHolder(inflater.inflate(R.layout.list_view_fileitem, parent, false))
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
