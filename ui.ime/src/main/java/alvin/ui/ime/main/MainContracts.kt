package alvin.ui.ime.main

import alvin.lib.mvp.contracts.IPresenter

interface MainContracts {

    interface IView : alvin.lib.mvp.contracts.IView {
        fun showInput(text: String)
        fun hideInputMethod()
    }

    interface Presenter : IPresenter {
        fun textInput(text: String)
    }
}
