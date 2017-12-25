package alvin.base.kotlin.dbflow

import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.lib.mvp.contracts.IPresenter
import alvin.lib.mvp.contracts.IView

object DBFlowContract {

    interface View : IView {
        fun personCreated(result: Person)
        fun showPersonEditError()
        fun showPersons(persons: List<Person>?)
        fun showPersonLoadError()
        fun personUpdated(person: Person)
        fun personDeleted(person: Person)
    }

    interface Presenter : IPresenter {
        fun loadPersons(gender: Gender?)
        fun savePerson(person: Person)
        fun updatePerson(person: Person)
        fun deletePerson(person: Person)
    }
}
