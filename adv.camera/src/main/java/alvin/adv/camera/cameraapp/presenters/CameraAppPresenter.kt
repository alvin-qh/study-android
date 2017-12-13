package alvin.adv.camera.cameraapp.presenters

import alvin.adv.camera.cameraapp.CameraAppContracts
import alvin.base.kotlin.lib.common.rx.RxManager
import alvin.lib.common.utils.Storages
import alvin.lib.mvp.adapters.ViewPresenterAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import io.reactivex.Single
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

class CameraAppPresenter
@Inject constructor(
        view: CameraAppContracts.View,
        @Named("photo_save_file_pattern") private val photoSaveFilePattern: String,
        private val storages: Storages,
        private val rxManager: RxManager
) : ViewPresenterAdapter<CameraAppContracts.View>(view), CameraAppContracts.Presenter {

    companion object {
        val TAG = CameraAppPresenter::class.simpleName

        val PHOTO_DIR = "Camera"
        val PHOTO_EXT = ".jpg"
        val CAMERA_RESULT_DATA_NAME = "data"
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
        val name = DateTimeFormatter.ofPattern(photoSaveFilePattern).format(LocalDateTime.now())
        val filename = "$name$PHOTO_EXT"
        try {
            val photoFile = storages.createImageCaptureFile(PHOTO_DIR, filename)
            withView { it.startCameraActivity(photoFile) }
        } catch (e: Exception) {
            Log.e(TAG, "Cannot create photo file", e)
            withView { it.showCannotCreatePhotoFileError() }
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
        val subscriber = rxManager.with(
                Single.create<Bitmap> {
                    try {
                        if (data != null && data.hasExtra(CAMERA_RESULT_DATA_NAME)) {
                            it.onSuccess(data.getParcelableExtra(CAMERA_RESULT_DATA_NAME))
                        } else if (photoFile != null) {
                            it.onSuccess(BitmapFactory.decodeFile(photoFile.absolutePath))
                        } else {
                            throw IllegalArgumentException("Invalid image")
                        }
                    } catch (e: Exception) {
                        it.onError(e)
                    }
                }
        )
        subscriber.subscribe(
                { bitmap -> withView { it.showBitmap(bitmap) } },
                { throwable ->
                    Log.e(TAG, "Error when decode bitmap", throwable)
                    withView { it.showDecodeBitmapError() }
                }
        )
    }
}
