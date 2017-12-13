package alvin.adv.camera.cameraapp.presenters

import alvin.adv.camera.cameraapp.CameraAppContracts
import alvin.lib.mvp.adapters.ViewPresenterAdapter
import javax.inject.Inject

class CameraAppPresenter
@Inject constructor(view: CameraAppContracts.View) :
        ViewPresenterAdapter<CameraAppContracts.View>(view), CameraAppContracts.Presenter
