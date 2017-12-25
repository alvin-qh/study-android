package alvin.adv.camera.camera2.presenters

import alvin.adv.camera.camera2.Camera2Contracts
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import javax.inject.Inject

class Camera2Presenter
@Inject constructor(
        view: Camera2Contracts.View
) :
        PresenterAdapter<Camera2Contracts.View>(view),
        Camera2Contracts.Presenter
