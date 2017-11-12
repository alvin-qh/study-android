package alvin.ui.list.list.views

import alvin.ui.list.R
import alvin.ui.list.list.domain.models.FileItem
import alvin.ui.list.list.domain.models.FileType
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_list_by_list_view.*
import kotlinx.android.synthetic.main.view_file_item.view.*

class ListByListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_by_list_view)

        ButterKnife.bind(this)

        createListView()
    }

    private fun createListView() {
        val adapter = ListViewAdapter(this, listOf(
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
        list_view.adapter = adapter
        adapter.update()
    }
}

private class ListViewAdapter
constructor(
        context: Context,
        private var fileItems: List<FileItem>) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    fun update(fileItems: List<FileItem>? = null) {
        if (fileItems != null) {
            this.fileItems = fileItems
        }
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.view_file_item, parent, false)

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