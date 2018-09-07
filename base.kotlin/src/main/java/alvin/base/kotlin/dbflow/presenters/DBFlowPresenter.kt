package alvin.base.kotlin.dbflow.presenters

import alvin.base.kotlin.common.domain.models.Gender
import alvin.base.kotlin.common.domain.models.Person
import alvin.base.kotlin.dbflow.DBFlowContract
import alvin.base.kotlin.dbflow.domain.services.PersonService
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import javax.inject.Inject

class DBFlowPresenter
@Inject constructor(
        view: DBFlowContract.View
) :
        PresenterAdapter<DBFlowContract.View>(view),
        DBFlowContract.Presenter {

    private val service = PersonService()

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
