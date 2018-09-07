package alvin.base.kotlin.dagger

import alvin.base.kotlin.common.domain.models.Gender
import alvin.base.kotlin.common.domain.models.Person
import alvin.lib.mvp.contracts.IPresenter
import alvin.lib.mvp.contracts.IView

object DaggerContracts {

    interface View : IView {
        fun showPersons(persons: List<Person>?)
        fun personCreated(person: Person)
        fun personUpdated(person: Person)
        fun personDeleted(person: Person)
        fun showException(error: Throwable?)
    }

    interface Presenter : IPresenter {
        fun loadPersons(gender: Gender?)
        fun deletePerson(person: Person)
        fun updatePerson(person: Person)
        fun savePerson(person: Person)
    }
}
