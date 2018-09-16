package alvin.ui.layout.merge.views

import alvin.ui.layout.R
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

class MergeActivity : AppCompatActivity() {

    private val content = UserInfoContent(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_merge)
        content.initialize(View.OnClickListener {
            val info = content.userInfo
            val text = getString(R.string.text_user_info, info.gender.title, info.name)
            Snackbar.make(it, text, Snackbar.LENGTH_LONG).show()
        })
    }
}
