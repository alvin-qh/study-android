package alvin.adv.camera.main.presenters

import alvin.adv.camera.main.MainContracts
import alvin.lib.mvp.adapters.ViewPresenterAdapter
import javax.inject.Inject

class MainPresenter
@Inject constructor(view: MainContracts.View) :
        ViewPresenterAdapter<MainContracts.View>(view), MainContracts.Presenter
