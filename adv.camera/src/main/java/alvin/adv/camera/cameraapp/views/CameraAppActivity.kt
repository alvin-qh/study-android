package alvin.adv.camera.cameraapp.views

import alvin.adv.camera.BuildConfig
import alvin.adv.camera.R
import alvin.adv.camera.cameraapp.CameraAppContracts
import alvin.lib.common.utils.PackageManagers
import alvin.lib.common.utils.Permissions
import alvin.lib.common.utils.Storages
import alvin.lib.common.utils.Versions
import alvin.lib.mvp.views.AppCompatActivityView
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.CheckResult
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.camera_app_activity.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named


class CameraAppActivity : AppCompatActivityView<CameraAppContracts.Presenter>(), CameraAppContracts.View {

    companion object {
        val TAG = CameraAppActivity::class.simpleName
        val CAPTURE_PHOTO_REQUEST_CODE = 1
        val PERMISSION_REQUEST_CODE = 1
        val PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".provider"
    }

    @Inject lateinit var presenter: CameraAppContracts.Presenter
    @Inject lateinit var packageManagers: PackageManagers
    @Inject lateinit var storages: Storages

    @field:[Inject Named("photo_save_file_pattern")]
    lateinit var photoSaveFilePattern: String

    private var isImageCapturePackageExist = true

    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_app_activity)

        initialize()
    }

    private fun initialize() {
        btn_open_camera_app.onClick {
            if (isImageCapturePackageExist) {
                val permissions = Permissions(
                        this@CameraAppActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                when (permissions.requestPermissions(PERMISSION_REQUEST_CODE)) {
                    Permissions.Status.DENNY -> toast(R.string.error_permissions).show()
                    Permissions.Status.ALLOW -> photoFile = startImageCaptureActivity()
                    else -> {
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE &&
                Permissions.checkPermissionsWithResults(permissions, grantResults) == Permissions.Status.ALLOW) {
            photoFile = startImageCaptureActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        isImageCapturePackageExist = packageManagers.isImageCapturePackageExist
        if (!isImageCapturePackageExist) {
            ll_warning.visibility = View.VISIBLE
            btn_open_camera_app.isEnabled = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAPTURE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            val image: Bitmap = if (data != null && data.hasExtra("data")) {
                data.getParcelableExtra("data")
            } else {
                BitmapFactory.decodeFile(photoFile?.absolutePath)
            }
            iv_photo.image = BitmapDrawable(resources, image)
        }
    }

    override fun presenter(): CameraAppContracts.Presenter {
        return presenter
    }

    @CheckResult
    private fun startImageCaptureActivity(): File? {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) == null) {
            return null
        }
        val filename = DateTimeFormatter.ofPattern(photoSaveFilePattern)
                .format(LocalDateTime.now()) + ".jpg"
        val photoFile = storages.createImageCaptureFile("Camera", filename)

        val photoURI: Uri
        try {
            photoURI = if (Versions.VERSIONS_N.isEqualOrGreatThan) {
                FileProvider.getUriForFile(this, PROVIDER_AUTHORITY, photoFile)
            } else {
                Uri.fromFile(photoFile)
            }
        } catch (e: IOException) {
            Log.e(TAG, "Cannot visit external storage", e)
            toast(R.string.error_storage_not_invalid).show()
            return null
        }

        packageManagers.grantUriPermission(intent, photoURI,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, CAPTURE_PHOTO_REQUEST_CODE)

        return photoFile
    }
}
