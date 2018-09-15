package alvin.base.kotlin.dagger.presenters

import alvin.base.kotlin.common.domain.models.Gender
import alvin.base.kotlin.common.domain.models.Person
import alvin.base.kotlin.dagger.DaggerContracts
import alvin.base.kotlin.dagger.domain.services.PersonService
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import javax.inject.Inject

class DaggerPresenter @Inject constructor(
        view: DaggerContracts.View,
        private val service: PersonService
) :
        PresenterAdapter<DaggerContracts.View>(view),
        DaggerContracts.Presenter {

    override fun loadPersons(gender: Gender?) {
        service.findByGender(gender) { persons -> with { it.showPersons(persons) } }
    }

    override fun savePerson(person: Person) {
        service.create(person) { with { it.personCreated(person) } }
    }

    override fun updatePerson(person: Person) {
        service.update(person) { with { it.personUpdated(person) } }
    }

    override fun deletePerson(person: Person) {
        service.delete(person) { with { it.personDeleted(person) } }
    }
}
