package alvin.adv.permission.remoteservice.views

import alvin.adv.permission.R
import alvin.adv.permission.remoteservice.RemoteServiceContracts
import alvin.adv.permission.storage.views.StorageActivity
import alvin.lib.common.utils.Permissions
import alvin.lib.mvp.contracts.adapters.ActivityAdapter
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import kotlinx.android.synthetic.main.remote_service_activity.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class RemoteServiceActivity :
        ActivityAdapter<RemoteServiceContracts.Presenter>(),
        RemoteServiceContracts.IView {

    object serverConnect : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remote_service_activity)

        initialize()
    }

    override fun onResume() {
        super.onResume()

        val permissions = Permissions(this, "alvin.permissions.REMOTE_SERVICE")

        val status = permissions.requestPermissions(StorageActivity.PERMISSION_REQUEST_CODE) {
            toast(R.string.message_need_permission).show()
            true
        }
        if (status == Permissions.Status.ALLOWED) {
            btn_bind_service.isEnabled = true
            btn_unbind_service.isEnabled = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == StorageActivity.PERMISSION_REQUEST_CODE) {
            val status = Permissions.checkPermissionsWithResults(permissions, grantResults)
            if (status == Permissions.Status.ALLOWED) {
                btn_bind_service.isEnabled = true
                btn_unbind_service.isEnabled = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serverConnect)
    }

    private fun initialize() {
        supportActionBar?.elevation = 0f

        btn_bind_service.onClick {
            val intent = Intent("alvin.services.REMOTE_SERVICE")
            intent.`package` = "alvin.base.service"
            bindService(intent, serverConnect, Context.BIND_AUTO_CREATE)
        }

        btn_unbind_service.onClick {
            unbindService(serverConnect)
        }
    }
}
