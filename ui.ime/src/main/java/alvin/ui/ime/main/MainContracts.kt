package alvin.ui.ime.main

import alvin.lib.mvp.contracts.IPresenter
import alvin.lib.mvp.contracts.IView

interface MainContracts {

    interface View : IView {
        fun showInput(text: String)
        fun hideInputMethod()
    }

    interface Presenter : IPresenter {
        fun textInput(text: String)
    }
}
