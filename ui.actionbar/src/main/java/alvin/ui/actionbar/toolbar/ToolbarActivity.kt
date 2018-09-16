package alvin.ui.actionbar.toolbar

import alvin.ui.actionbar.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import kotlinx.android.synthetic.main.activity_simple_action.*
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.view_menu.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick

class ToolbarActivity : AppCompatActivity() {

    private var displayHomeAsUpEnabled = true
    private var displayShowHomeEnabled = true
    private var displayLogo = true
    private var menuItemShowAsAction = MenuItem.SHOW_AS_ACTION_IF_ROOM
    private var homeAsUpIcon = R.drawable.ic_arrow_back
    private var displayShowCustomEnabled = false

    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        setSupportActionBar(toolbar)
        setupActionbar()
        setupButtons()
    }

    private fun setupActionbar() {
        val bar = supportActionBar
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
            bar.setDisplayShowHomeEnabled(displayShowHomeEnabled)
            bar.setDisplayUseLogoEnabled(displayLogo)
            bar.setLogo(R.drawable.actionbar_space_between_icon_and_title)
            bar.setHomeAsUpIndicator(homeAsUpIcon)
            bar.subtitle = "Sub title"

            bar.setDisplayShowCustomEnabled(true)
            bar.setCustomView(R.layout.view_menu)

            val adapter = ArrayAdapter.createFromResource(this,
                    R.array.sa_planets, R.layout.spinner_dropdown)
            adapter.setDropDownViewResource(R.layout.spinner_item)
            sp_planets.adapter = adapter

            bar.setDisplayShowCustomEnabled(displayShowCustomEnabled)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            this.menu = menu
        }
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                longToast("Menu item '${item.title}' clicked")
                true
            }
            android.R.id.home -> {
                longToast("Menu item 'Home' clicked")
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupButtons() {
        btn_hide_action_bar.onClick {
            it as Button

            val bar = supportActionBar
            if (bar != null) {
                if (bar.isShowing) {
                    bar.hide()
                    it.setText(R.string.btn_show_actionbar)
                } else {
                    bar.show()
                    it.setText(R.string.btn_hide_actionbar)
                }
            }
        }

        btn_hide_up_home.onClick {
            it as Button

            val bar = supportActionBar
            if (bar != null) {
                displayHomeAsUpEnabled = !displayHomeAsUpEnabled
                bar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
                it.setText(if (displayHomeAsUpEnabled) R.string.btn_hide_up_home else R.string.btn_show_up_home)
            }
        }

        btn_hide_logo.onClick {
            it as Button

            val bar = supportActionBar
            if (bar != null) {
                displayLogo = !displayLogo
                bar.setDisplayUseLogoEnabled(displayLogo)
                it.setText(if (displayLogo) R.string.btn_hide_logo else R.string.btn_show_logo)
            }
        }

        btn_menu_layout.onClick {
            menuItemShowAsAction = if (menuItemShowAsAction == MenuItem.SHOW_AS_ACTION_IF_ROOM) {
                MenuItem.SHOW_AS_ACTION_NEVER
            } else {
                MenuItem.SHOW_AS_ACTION_IF_ROOM
            }

            for (i in 0 until menu.size()) {
                menu.getItem(i).setShowAsAction(menuItemShowAsAction)
            }
        }

        btn_up_home_icon.onClick {
            homeAsUpIcon = if (homeAsUpIcon == R.drawable.ic_arrow_back) {
                R.drawable.ic_arrow_next
            } else {
                R.drawable.ic_arrow_back
            }

            supportActionBar?.setHomeAsUpIndicator(homeAsUpIcon)
        }

        btn_custom_view.onClick {
            displayShowCustomEnabled = !displayShowCustomEnabled

            supportActionBar?.setDisplayShowCustomEnabled(displayShowCustomEnabled)
            supportActionBar?.setDisplayShowTitleEnabled(!displayShowCustomEnabled)
        }
    }
}
