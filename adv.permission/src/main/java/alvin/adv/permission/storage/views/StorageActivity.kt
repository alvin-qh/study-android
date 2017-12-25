package alvin.adv.permission.storage.views

import alvin.adv.permission.R
import alvin.adv.permission.storage.StorageContracts
import alvin.adv.permission.storage.models.Gender
import alvin.adv.permission.storage.models.Person
import alvin.lib.common.utils.Permissions
import alvin.lib.mvp.contracts.adapters.ActivityAdapter
import android.Manifest
import android.os.Bundle
import kotlinx.android.synthetic.main.storage_activty.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.time.LocalDate

/**
 * Ask permissions.
 *
 * Add flowing configs in `AndroidManifest.xml` file:
 *
 * ```
 *  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * ```
 *
 * Use `Permissions` class to ask permissions
 *
 * @see StorageActivity.onResume
 * @see Permissions
 */
class StorageActivity :
        ActivityAdapter<StorageContracts.Presenter>(),
        StorageContracts.View {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.storage_activty)

        initialize()
    }

    private fun initialize() {
        btn_save.onClick {
            val gender = when (rg_gender.checkedRadioButtonId) {
                R.id.rb_gender_female -> Gender.FEMALE
                else -> Gender.MALE
            }
            val birthday = LocalDate.of(dp_birthday.year, dp_birthday.month, dp_birthday.dayOfMonth)

            val person = Person(
                    et_name.text.toString(),
                    gender,
                    birthday,
                    et_remark.text.toString()
            )
            presenter.savePerson(person)
        }
    }

    /**
     * Ask permissions when view be resumed.
     *
     * When permissions asked, the `onRequestPermissionsResult` method should be callback after
     *
     * @see StorageActivity.onRequestPermissionsResult
     * @see Permissions.requestPermissions
     * @see Permissions.Status
     */
    override fun onResume() {
        super.onResume()

        val permissions = Permissions(this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val status = permissions.requestPermissions(PERMISSION_REQUEST_CODE) {
            toast(R.string.message_need_permission).show()
            true
        }
        if (status == Permissions.Status.ALLOWED) {
            presenter.loadPerson()
            btn_save.isEnabled = true
        }
    }

    /**
     * @param requestCode An int number passed when permissions were asked
     * @param permissions Names of permissions would be asked
     * @param grantResults Results of permissions would be asked
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val status = Permissions.checkPermissionsWithResults(permissions, grantResults)
            if (status == Permissions.Status.ALLOWED) {
                presenter.loadPerson()
                btn_save.isEnabled = true
            }
        }
    }

    override fun saveComplete() {
        toast(R.string.message_save_complete).show()
    }

    override fun saveFailed() {
        toast(R.string.message_save_failed).show()
    }

    override fun showPerson(person: Person) {
        et_name.setText(person.name)
        rg_gender.check(if (person.gender == Gender.MALE) R.id.rb_gender_male else R.id.rb_gender_female)
        dp_birthday.updateDate(person.birthday.year, person.birthday.month.value, person.birthday.dayOfMonth)
        et_remark.setText(person.remark)
    }

    override fun loadFailed() {
        toast(R.string.message_load_failed).show()
    }
}
