package alvin.adv.camera.cameraapp.views

import alvin.adv.camera.BuildConfig
import alvin.adv.camera.R
import alvin.adv.camera.cameraapp.CameraAppContracts
import alvin.lib.common.utils.PackageManagers
import alvin.lib.common.utils.Permissions
import alvin.lib.common.utils.Versions
import alvin.lib.mvp.views.AppCompatActivityView
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.view.View
import kotlinx.android.synthetic.main.camera_app_activity.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.io.File
import javax.inject.Inject


class CameraAppActivity : AppCompatActivityView<CameraAppContracts.Presenter>(), CameraAppContracts.View {

    companion object {
        val CAMERA_URI_PERMISSION = Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        val CAPTURE_PHOTO_REQUEST_CODE = 1
        val PERMISSION_REQUEST_CODE = 1
        val PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider"
    }

    @Inject lateinit var packageManagers: PackageManagers

    private var isImageCapturePackageExist = true

    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_app_activity)

        initialize()
    }

    /**
     * Initialize activity and check permissions about [Manifest.permission.READ_EXTERNAL_STORAGE]
     * and [Manifest.permission.WRITE_EXTERNAL_STORAGE].
     *
     * @see Permissions
     * @see Permissions.requestPermissions
     * @see Permissions.Status.ALLOW
     * @see Permissions.Status.DENY
     * @see Permissions.Status.REQUIRED
     *
     * @see alvin.adv.camera.cameraapp.presenters.CameraAppPresenter.makePhotoFileUri
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initialize() {
        btn_open_camera_app.onClick {
            if (isImageCapturePackageExist) {
                val permissions = Permissions(
                        this@CameraAppActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)

                // Request permissions
                when (permissions.requestPermissions(PERMISSION_REQUEST_CODE)) {
                    Permissions.Status.DENY -> toast(R.string.error_permissions).show()
                    Permissions.Status.ALLOW -> presenter.makePhotoFileUri()
                    else -> {
                    }
                }
            }
        }
    }

    /**
     * When permission request is finished, this method should be callback.
     *
     * @param requestCode A int value was passed when permissions request
     * @param permissions All request permissions
     * @param grantResults Results of each permission granted
     *
     * @see Permissions.checkPermissionsWithResults
     *
     * @see Permissions.Status.ALLOW
     * @see Permissions.Status.DENY
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE &&
                Permissions.checkPermissionsWithResults(permissions, grantResults) == Permissions.Status.ALLOW) {
            presenter.makePhotoFileUri()
        }
    }

    /**
     * Check if 'Image Capture Activity' exist.
     *
     * If some system did not install camera application, the 'Image Capture Activity' may not exist
     *
     * @see PackageManagers.isImageCapturePackageExist
     */
    override fun onResume() {
        super.onResume()
        isImageCapturePackageExist = packageManagers.isImageCapturePackageExist
        if (!isImageCapturePackageExist) {
            ll_warning.visibility = View.VISIBLE
            btn_open_camera_app.isEnabled = false
        }
    }

    /**
     * Start 'Image capture activity' to get photo image and save image into given file
     *
     * The steps are:
     * 1. Check if 'Image capture activity' available.
     * 2. Get URI from given file
     * 3. Start the 'Image capture activity' for result with given file as parameter
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    override fun startCameraActivity(photoFile: File) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) == null) {
            return
        }
        intent.addFlags(CAMERA_URI_PERMISSION)

        val photoURI: Uri = if (Versions.VERSIONS_N.isEqualOrGreatThan) {
            FileProvider.getUriForFile(this, PROVIDER_AUTHORITY, photoFile)
        } else {
            Uri.fromFile(photoFile)
        }

        packageManagers.grantUriPermission(intent, photoURI, CAMERA_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, CAPTURE_PHOTO_REQUEST_CODE)

        this.photoFile = photoFile
    }

    /**
     * If other activity was start by [android.app.Activity.startActivityForResult] method,
     * this method will be called after other activity was closed, and result from other activity
     * should be given to from arguments
     *
     * @param requestCode The int value was passed when the other activity was started
     * @param resultCode [android.app.Activity.RESULT_OK] or [android.app.Activity.RESULT_CANCELED]
     * @param data If no URI was passed to 'Image capture activity', it include photo image object
     *
     * @see alvin.adv.camera.cameraapp.presenters.CameraAppPresenter.decodeImage
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAPTURE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            presenter.decodeImage(data, photoFile)
        }
    }

    override fun showCannotCreatePhotoFileError() {
        toast(R.string.error_storage_not_invalid).show()
    }

    /**
     * Convert bitmap as drawable instance and display it on view
     *
     * @see BitmapDrawable
     */
    override fun showBitmap(bitmap: Bitmap) {
        iv_photo.image = BitmapDrawable(resources, bitmap)
    }

    override fun showDecodeBitmapError() {
        toast(R.string.error_cannot_decode_bitmap).show()
    }
}
