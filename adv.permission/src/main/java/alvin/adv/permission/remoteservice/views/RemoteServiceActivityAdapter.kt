package alvin.adv.permission.remoteservice.views

import alvin.adv.permission.R
import alvin.adv.permission.remoteservice.RemoteServiceContracts
import alvin.lib.mvp.contracts.adapters.ActivityAdapter
import android.os.Bundle
import kotlinx.android.synthetic.main.remote_service_activity.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RemoteServiceActivityAdapter :
        ActivityAdapter<RemoteServiceContracts.Presenter>(),
        RemoteServiceContracts.IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remote_service_activity)

        initialize()
    }

    private fun initialize() {
        supportActionBar?.elevation = 0f

        btn_bind_service.onClick {

        }

        btn_unbind_service.onClick {

        }
    }
}
