package alvin.base.kotlin.dagger.presenters

import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.dagger.DaggerContracts
import alvin.base.kotlin.dagger.domain.services.PersonService
import alvin.lib.common.rx.RxDecorator
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import io.reactivex.Single
import javax.inject.Inject

class DaggerPresenter
@Inject constructor(
        view: DaggerContracts.View,
        private val service: PersonService,
        private val rxDecorator: RxDecorator
) :
        PresenterAdapter<DaggerContracts.View>(view),
        DaggerContracts.Presenter {

    override fun loadPersons(gender: Gender?) {
        rxDecorator.de(
                Single.create<List<Person>> {
                    try {
                        it.onSuccess(service.findByGender(gender))
                    } catch (e: Exception) {
                        it.onError(e)
                    }
                }
        ).subscribe(
                { persons -> with { it.showPersons(persons) } },
                { throwable -> with { it.showException(throwable) } }
        )
    }

    override fun savePerson(person: Person) {
        rxDecorator.de(
                Single.create<Person> {
                    try {
                        service.create(person)
                        it.onSuccess(person)
                    } catch (e: Exception) {
                        it.onError(e)
                    }
                }
        ).subscribe(
                { p -> with { it.personCreated(p) } },
                { throwable -> with { it.showException(throwable) } }
        )
    }

    override fun updatePerson(person: Person) {
        rxDecorator.de(
                Single.create<Person> {
                    try {
                        service.update(person)
                        it.onSuccess(person)
                    } catch (e: Exception) {
                        it.onError(e)
                    }
                }
        ).subscribe(
                { p -> with { it.personUpdated(p) } },
                { throwable -> with { it.showException(throwable) } }
        )
    }

    override fun deletePerson(person: Person) {
        rxDecorator.de(
                Single.create<Person> {
                    try {
                        service.delete(person)
                        it.onSuccess(person)
                    } catch (e: Exception) {
                        it.onError(e)
                    }
                }
        ).subscribe(
                { p -> with { it.personDeleted(p) } },
                { throwable -> with { it.showException(throwable) } }
        )
    }
}
