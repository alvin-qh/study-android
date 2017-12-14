package alvin.ui.listing.list.listview

import alvin.lib.mvp.contracts.IPresenter
import alvin.lib.mvp.contracts.IView
import alvin.ui.listing.domain.models.FileItem

object ListViewContracts {

    interface View : IView {
        fun showFileItem(fileItems: List<FileItem>)
    }

    interface Presenter : IPresenter {
        fun makeListContent()
    }
}
