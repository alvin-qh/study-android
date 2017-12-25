package alvin.adv.permission.remoteservice.presenters

import alvin.adv.permission.remoteservice.RemoteServiceContracts
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import javax.inject.Inject

class RemoteServicePresenter
@Inject constructor(
        view: RemoteServiceContracts.IView
) :
        PresenterAdapter<RemoteServiceContracts.IView>(view),
        RemoteServiceContracts.Presenter
