package alvin.ui.style.shape.views

import alvin.ui.style.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlin.reflect.KClass

class ShapeActivity : AppCompatActivity() {

    private val fragmentHolder = hashMapOf<KClass<out Fragment>, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shape)

        changeFragmentView(
                fragmentHolder.computeIfAbsent(RectangleFragment::class) {
                    RectangleFragment.newInstance()
                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shape, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item != null) {
            changeFragmentView(when (item.itemId) {
                R.id.action_shape_rectangle -> fragmentHolder.computeIfAbsent(RectangleFragment::class) {
                    RectangleFragment.newInstance()
                }
                R.id.action_shape_oval -> fragmentHolder.computeIfAbsent(OvalFragment::class) {
                    OvalFragment.newInstance()
                }
                else -> throw IllegalArgumentException()
            })
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun changeFragmentView(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentHolder.clear()
    }
}
