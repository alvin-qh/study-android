package alvin.ui.ime.main.presenters

import alvin.lib.mvp.adapters.ViewPresenterAdapter
import alvin.ui.ime.main.MainContracts
import javax.inject.Inject

class MainPresenter
@Inject constructor(view: MainContracts.View) :
        ViewPresenterAdapter<MainContracts.View>(view), MainContracts.Presenter {

    override fun textInput(text: String) {
        if (!text.isEmpty()) {
            withView {
                it.showInput(text)
                it.hideInputMethod()
            }
        }
    }
}
