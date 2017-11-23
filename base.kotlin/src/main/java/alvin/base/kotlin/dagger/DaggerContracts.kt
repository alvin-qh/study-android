package alvin.base.kotlin.dagger

import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.lib.mvp.IPresenter
import alvin.lib.mvp.IView

interface DaggerContracts {

    interface View : IView {
        fun getQueryGender(): Gender?
        fun showPersons(persons: List<Person>?)
        fun personCreated(person: Person)
        fun personUpdated(person: Person)
        fun personDeleted(person: Person)
        fun showException(error: Throwable?)
    }

    interface Presenter : IPresenter {
        fun reloadPersons()
        fun deletePerson(person: Person)
        fun updatePerson(person: Person)
        fun savePerson(person: Person)
    }
}
