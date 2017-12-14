package alvin.ui.listing.list.listview.persenters

import alvin.lib.mvp.adapters.ViewPresenterAdapter
import alvin.ui.listing.domain.models.FileItem
import alvin.ui.listing.list.listview.ListViewContracts
import javax.inject.Inject
import javax.inject.Named

class ListViewPresenter
@Inject constructor(
        view: ListViewContracts.View,
        @Named("data-list") private val fileItems: List<FileItem>
) : ViewPresenterAdapter<ListViewContracts.View>(view), ListViewContracts.Presenter {

    override fun makeListContent() {
        withView { it.showFileItem(fileItems) }
    }
}
