package alvin.adv.camera.cameraapp

import alvin.lib.mvp.contracts.IPresenter
import alvin.lib.mvp.contracts.IView
import android.content.Intent
import android.graphics.Bitmap
import java.io.File

object CameraAppContracts {

    interface View : IView {
        fun startCameraActivity(photoFile: File)
        fun showCannotCreatePhotoFileError()
        fun showBitmap(bitmap: Bitmap)
        fun showDecodeBitmapError()
    }

    interface Presenter : IPresenter {
        fun makePhotoFileUri()
        fun decodeImage(data: Intent?, photoFile: File?)
    }
}
