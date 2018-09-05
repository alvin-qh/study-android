package alvin.ui.ime.main.presenters

import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import alvin.ui.ime.main.MainContracts
import javax.inject.Inject

class MainPresenter
@Inject constructor(view: MainContracts.IView) :
        PresenterAdapter<MainContracts.IView>(view), MainContracts.Presenter {

    override fun textInput(text: String) {
        if (!text.isEmpty()) {
            with {
                it.showInput(text)
                it.hideInputMethod()
            }
        }
    }
}
