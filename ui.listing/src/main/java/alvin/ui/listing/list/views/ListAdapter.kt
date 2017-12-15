package alvin.ui.listing.list.views

import alvin.ui.listing.R
import alvin.ui.listing.domain.models.FileItem
import alvin.ui.listing.domain.models.FileType
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_view_fileitem.view.*

class ListAdapter
constructor(context: Context) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)
    private var fileItems: List<FileItem> = emptyList()

    fun update(fileItems: List<FileItem>? = null) {
        if (fileItems != null) {
            this.fileItems = fileItems
        }
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.list_view_fileitem, parent, false)

        var holder: ViewHolder? = view.tag as? ViewHolder
        if (holder == null) {
            holder = ViewHolder(view)
            view.tag = holder
        }

        val item = getItem(position)
        val icon = when (item.type) {
            FileType.FILE -> R.drawable.ic_file
            else -> R.drawable.ic_folder
        }
        holder.ivLogo.setImageResource(icon)
        holder.tvFilename.text = item.name

        return view
    }

    override fun getItem(position: Int): FileItem {
        return fileItems[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getCount(): Int {
        return fileItems.size
    }

    class ViewHolder
    constructor(view: View) {
        val ivLogo = view.iv_logo!!
        val tvFilename = view.tv_file_name!!
    }
}
