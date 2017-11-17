package alvin.base.kotlin.dbflow.views

import alvin.base.kotlin.R
import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.common.views.PersonDialog
import alvin.base.kotlin.common.views.PersonListAdapter
import alvin.base.kotlin.dbflow.DBFlowContract
import alvin.base.kotlin.dbflow.presenters.DBFlowPresenter
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.dagger_activity.*

class DBFlowActivity : AppCompatActivity(), DBFlowContract.View {

    private lateinit var presenter: DBFlowContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dbflow_activity)

        ButterKnife.bind(this)

        initializeListView()

        rg_gender.setOnCheckedChangeListener { _, _ ->
            presenter.reloadPersons()
        }

        presenter = DBFlowPresenter(this)
        presenter.onCreate()
    }

    private fun initializeListView() {
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

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    @OnClick(R.id.fab)
    fun onAddButtonClick() {
        val dlg = PersonDialog.Builder(this).create(R.string.title_form_dialog)
        dlg.onConfirmClickListener = View.OnClickListener {
            presenter.savePerson(Person(dlg.name, dlg.gender, dlg.birthday))
            dlg.dismiss()
        }
        dlg.show()
    }

    override fun personCreated(result: Person) {
        presenter.reloadPersons()
        Toast.makeText(this, R.string.msg_person_created, Toast.LENGTH_SHORT).show()
    }

    override fun showPersonEditError() {
        Toast.makeText(this, R.string.error_update_persons, Toast.LENGTH_SHORT).show()
    }

    override fun showPersons(persons: List<Person>?) {
        if (persons != null) {
            val adapter = rv_persons.adapter as PersonListAdapter
            adapter.update(persons)
        }
    }

    override fun showPersonLoadError() {
        Toast.makeText(this, R.string.error_load_persons, Toast.LENGTH_LONG).show()
    }

    override fun getQueryGender(): Gender? {
        return when (rg_gender.checkedRadioButtonId) {
            R.id.rb_male -> Gender.MALE
            R.id.rb_female -> Gender.FEMALE
            else -> null
        }
    }

    override fun personUpdated(person: Person) {
        Toast.makeText(this, R.string.msg_person_updated, Toast.LENGTH_SHORT).show()
        presenter.reloadPersons()
    }

    override fun personDeleted(person: Person) {
        Toast.makeText(this, R.string.msg_person_deleted, Toast.LENGTH_SHORT).show()
        presenter.reloadPersons()
    }

    override fun context(): Context {
        return this
    }
}
