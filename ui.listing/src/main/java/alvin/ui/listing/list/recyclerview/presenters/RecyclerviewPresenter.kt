package alvin.ui.listing.list.recyclerview.presenters

import alvin.lib.mvp.adapters.ViewPresenterAdapter
import alvin.ui.listing.domain.models.FileItem
import alvin.ui.listing.list.recyclerview.RecyclerViewContracts
import javax.inject.Inject
import javax.inject.Named

class RecyclerviewPresenter
@Inject constructor(
        view: RecyclerViewContracts.View,
        @Named("data-list") private val fileItems: List<FileItem>
) : ViewPresenterAdapter<RecyclerViewContracts.View>(view), RecyclerViewContracts.Presenter {

    override fun makeListContent() {
        withView { it.showFileItem(fileItems) }
    }
}
