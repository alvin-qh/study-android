package alvin.adv.camera.main.presenters

import alvin.adv.camera.main.MainContracts
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import javax.inject.Inject

class MainPresenter
@Inject constructor(
        view: MainContracts.View
) :
        PresenterAdapter<MainContracts.View>(view),
        MainContracts.Presenter
