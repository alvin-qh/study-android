package alvin.base.kotlin.dbflow.presenters

import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.dbflow.DBFlowContract
import alvin.base.kotlin.dbflow.domain.services.PersonService
import alvin.lib.common.rx.RxDecorator
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DBFlowPresenter
@Inject constructor(
        view: DBFlowContract.View
) :
        PresenterAdapter<DBFlowContract.View>(view),
        DBFlowContract.Presenter {

    private val service = PersonService()

    private val rxDecorator = RxDecorator.newBuilder()
            .subscribeOn { Schedulers.io() }
            .observeOn { AndroidSchedulers.mainThread() }
            .build()

    override fun loadPersons(gender: Gender?) {
        rxDecorator.de(Single.create<List<Person>> {
            try {
                it.onSuccess(service.findByGender(gender))
            } catch (e: Exception) {
                it.onError(e)
            }
        }).subscribe(
                { persons -> with { it.showPersons(persons) } },
                { with { it.showPersonLoadError() } }
        )
    }

    override fun savePerson(person: Person) {
        rxDecorator.de(Single.create<Person> {
            try {
                service.create(person)
                it.onSuccess(person)
            } catch (e: Exception) {
                it.onError(e)
            }
        }).subscribe(
                { p -> with { it.personCreated(p) } },
                { with { it.showPersonEditError() } }
        )
    }

    override fun updatePerson(person: Person) {
        rxDecorator.de(Single.create<Person> {
            try {
                service.update(person)
                it.onSuccess(person)
            } catch (e: Exception) {
                it.onError(e)
            }
        }).subscribe(
                { p -> with { it.personUpdated(p) } },
                { with { it.showPersonEditError() } }
        )
    }

    override fun deletePerson(person: Person) {
        rxDecorator.de(Single.create<Person> {
            try {
                service.delete(person)
                it.onSuccess(person)
            } catch (e: Exception) {
                it.onError(e)
            }
        }).subscribe(
                { p -> with { it.personDeleted(p) } },
                { with { it.showPersonEditError() } }
        )
    }
}
