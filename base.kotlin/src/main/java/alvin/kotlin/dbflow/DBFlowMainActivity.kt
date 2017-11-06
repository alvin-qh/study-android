package alvin.kotlin.dbflow

import alvin.kotlin.R
import alvin.kotlin.dbflow.domain.models.Gender
import alvin.kotlin.dbflow.domain.models.Person
import alvin.kotlin.dbflow.presenters.DBFlowPresenter
import alvin.kotlin.dbflow.views.PersonDialog
import alvin.kotlin.dbflow.views.PersonListAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.raizlabs.android.dbflow.kotlinextensions.delete
import kotlinx.android.synthetic.main.activity_dbflow_main.*

class DBFlowMainActivity : AppCompatActivity(), DBFlowContract.View {

    private lateinit var presenter: DBFlowContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dbflow_main)

        ButterKnife.bind(this)

        val adapter = PersonListAdapter(this, emptyList())
        rv_persons.adapter = adapter
        rv_persons.itemAnimator = DefaultItemAnimator()
        rv_persons.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.onItemEditListener = { person -> onPersonEdit(person) }

        adapter.onItemDeleteListener = { person -> onPersonDelete(person) }

        presenter = DBFlowPresenter(this)
        presenter.doCreated()

        rg_gender.setOnCheckedChangeListener({ _, _ -> presenter.reloadPersons() })
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
        presenter.doStarted()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.doDestroyed()
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
    }

    override fun showPersonCreateError() {
    }

    override fun showPersons(persons: List<Person>?) {
        if (persons != null) {
            val adapter = rv_persons.adapter as PersonListAdapter
            adapter.update(persons)
        }
    }

    override fun showPersonLoadError() {
    }

    override fun getQueryGender(): Gender? {
        return when (rg_gender.checkedRadioButtonId) {
            R.id.rb_male -> Gender.MALE
            R.id.rb_female -> Gender.FEMALE
            else -> null
        }
    }

    override fun personUpdated(person: Person) {
        presenter.reloadPersons()
    }
}