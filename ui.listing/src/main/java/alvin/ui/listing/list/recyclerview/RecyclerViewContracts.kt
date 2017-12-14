package alvin.ui.listing.list.recyclerview

import alvin.lib.mvp.contracts.IPresenter
import alvin.lib.mvp.contracts.IView
import alvin.ui.listing.domain.models.FileItem

object RecyclerViewContracts {

    interface View : IView {
        fun showFileItem(fileItems: List<FileItem>)
    }

    interface Presenter : IPresenter {
        fun makeListContent()
    }
}
