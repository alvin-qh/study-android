package alvin.list.list.views

import alvin.list.R
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

class ListByRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_list_by_recycler_view)

        ButterKnife.bind(this)
    }
}