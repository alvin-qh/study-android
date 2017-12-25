package alvin.base.kotlin.dbflow.views

import alvin.base.kotlin.R
import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.common.views.PersonDialog
import alvin.base.kotlin.common.views.PersonListAdapter
import alvin.base.kotlin.dbflow.DBFlowContract
import alvin.lib.mvp.contracts.adapters.ActivityAdapter
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.dagger_activity.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class DBFlowActivity :
        ActivityAdapter<DBFlowContract.Presenter>(),
        DBFlowContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dbflow_activity)

        initialize()
    }

    private fun initialize() {
        val adapter = PersonListAdapter(this, emptyList())
        rv_persons.adapter = adapter
        rv_persons.itemAnimator = DefaultItemAnimator()
        rv_persons.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.onItemEditListener = { person ->
            onPersonEdit(person)
        }
        adapter.onItemDeleteListener = { person ->
            onPersonDelete(person)
        }

        rg_gender.onCheckedChange { _, _ ->
            presenter.loadPersons(getGender())
        }

        fab.onClick {
            val dlg = PersonDialog.Builder(this@DBFlowActivity).create(R.string.title_form_dialog)
            dlg.onConfirmClickListener = View.OnClickListener {
                presenter.savePerson(Person(dlg.name, dlg.gender, dlg.birthday))
                dlg.dismiss()
            }
            dlg.show()
        }
    }

    private fun onPersonDelete(person: Person) {
        presenter.deletePerson(person)
    }

    private fun onPersonEdit(person: Person) {
        val dlg = PersonDialog.Builder(this).create(R.string.title_form_dialog)
        dlg.name = person.name
        dlg.gender = person.gender
        dlg.birthday = person.birthday

        dlg.onConfirmClickListener = View.OnClickListener {
            person.name = dlg.name
            person.gender = dlg.gender
            person.birthday = dlg.birthday
            presenter.updatePerson(person)
            dlg.dismiss()
        }
        dlg.show()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadPersons(getGender())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun personCreated(result: Person) {
        presenter.loadPersons(getGender())
        toast(R.string.msg_person_created).show()
    }

    override fun showPersonEditError() {
        toast(R.string.error_update_persons).show()
    }

    override fun showPersons(persons: List<Person>?) {
        if (persons != null) {
            (rv_persons.adapter as PersonListAdapter).update(persons)
        }
    }

    override fun showPersonLoadError() {
        toast(R.string.error_load_persons).show()
    }

    private fun getGender(): Gender? {
        return when (rg_gender.checkedRadioButtonId) {
            R.id.rb_male -> Gender.MALE
            R.id.rb_female -> Gender.FEMALE
            else -> null
        }
    }

    override fun personUpdated(person: Person) {
        toast(R.string.msg_person_updated).show()
        presenter.loadPersons(getGender())
    }

    override fun personDeleted(person: Person) {
        toast(R.string.msg_person_deleted).show()
        presenter.loadPersons(getGender())
    }
}
