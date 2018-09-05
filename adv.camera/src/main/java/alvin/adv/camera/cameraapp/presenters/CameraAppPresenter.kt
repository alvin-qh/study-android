package alvin.adv.camera.cameraapp.presenters

import alvin.adv.camera.cameraapp.CameraAppContracts
import alvin.lib.common.rx.RxDecorator
import alvin.lib.common.rx.RxType
import alvin.lib.common.utils.Storages
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import io.reactivex.Single
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CameraAppPresenter
@Inject constructor(
        view: CameraAppContracts.View,
        private val storages: Storages,
        @RxType.IO private val rxDecoratorBuilder: RxDecorator.Builder
) :
        PresenterAdapter<CameraAppContracts.View>(view),
        CameraAppContracts.Presenter {

    companion object {
        val TAG = CameraAppPresenter::class.simpleName

        const val PHOTO_DIR = "Camera"
        const val PHOTO_EXT = ".jpg"
        const val CAMERA_RESULT_DATA_NAME = "data"

        const val PHOTO_SAVE_FILE_PATTERN = "yyyyMMddHHmmssSSS"
    }

    /**
     * Create file for saving photo.
     *
     * Save photo file on external storage, the public DCIM directory.
     *
     * @see Storages.createImageCaptureFile
     *
     * @see alvin.adv.camera.cameraapp.views.CameraAppActivity.startCameraActivity
     * @see alvin.adv.camera.cameraapp.views.CameraAppActivity.showCannotCreatePhotoFileError
     */
    override fun makePhotoFileUri() {
        val name = DateTimeFormatter.ofPattern(PHOTO_SAVE_FILE_PATTERN).format(LocalDateTime.now())
        val filename = "$name$PHOTO_EXT"
        try {
            val photoFile = storages.createImageCaptureFile(PHOTO_DIR, filename)
            with { it.startCameraActivity(photoFile) }
        } catch (e: Exception) {
            Log.e(TAG, "Cannot create photo file", e)
            with { it.showCannotCreatePhotoFileError() }
        }
    }

    /**
     * There are two ways to get image from 'Image Capture Activity' result.
     *
     * 1. If no URI was given to 'Image Capture Activity', the result included photo as bitmap image,
     * it's a parcelable data named 'data' <br>
     *
     * 2. If an URI was given, and it point to a file, the photo image should saved into this file,
     * to read the file to get the photo image
     *
     * @see Intent.hasExtra
     * @see Intent.getParcelableArrayExtra
     *
     * @see BitmapFactory.decodeFile
     *
     * @see alvin.adv.camera.cameraapp.views.CameraAppActivity.showBitmap
     * @see alvin.adv.camera.cameraapp.views.CameraAppActivity.showDecodeBitmapError
     */
    override fun decodeImage(data: Intent?, photoFile: File?) {
        val decorator = rxDecoratorBuilder.build()
        decorator.de<Bitmap>(Single.create {
            if (data != null && data.hasExtra(CAMERA_RESULT_DATA_NAME)) {
                it.onSuccess(data.getParcelableExtra(CAMERA_RESULT_DATA_NAME))
            } else if (photoFile != null) {
                it.onSuccess(BitmapFactory.decodeFile(photoFile.absolutePath))
            } else {
                throw IllegalArgumentException("Invalid image")
            }
        }).subscribe(
                { bitmap -> with { it.showBitmap(bitmap) } },
                { throwable ->
                    Log.e(TAG, "Error when decode bitmap", throwable)
                    with { it.showDecodeBitmapError() }
                }
        )
    }
}
