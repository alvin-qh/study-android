package alvin.kotlin.dbflow

import alvin.kotlin.dbflow.domain.models.Gender
import alvin.kotlin.dbflow.domain.models.Person

object DBFlowContract {

    interface View {
        fun personCreated(result: Person)
        fun showPersonEditError()
        fun getQueryGender(): Gender?
        fun showPersons(persons: List<Person>?)
        fun showPersonLoadError()
        fun personUpdated(person: Person)
        fun personDeleted(person: Person)
    }

    interface Presenter {
        fun doCreated()
        fun doStarted()
        fun doDestroyed()
        fun reloadPersons()
        fun savePerson(person: Person)
        fun updatePerson(person: Person)
        fun deletePerson(person: Person)
    }
}