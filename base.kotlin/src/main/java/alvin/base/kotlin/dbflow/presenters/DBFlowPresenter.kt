package alvin.base.kotlin.dbflow.presenters

import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.dbflow.DBFlowContract
import alvin.base.kotlin.dbflow.domain.repositories.PersonRepository
import alvin.base.kotlin.lib.common.rx.RxManager
import alvin.lib.mvp.PresenterAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DBFlowPresenter(val view: DBFlowContract.View) :
        PresenterAdapter<DBFlowContract.View>(view),
        DBFlowContract.Presenter {

    private val personRepository = PersonRepository()

    private val rxManager = RxManager.newBuilder()
            .withSubscribeOn { Schedulers.io() }
            .withObserveOn { AndroidSchedulers.mainThread() }
            .build()

    override fun started() {
        super.started()
        reloadPersons()
    }

    override fun destroyed() {
        super.destroyed();
        rxManager.clear()
    }

    override fun reloadPersons() {
        val subscriber = rxManager.single<List<Person>> { emitter ->
            withView { view ->
                try {
                    emitter.onSuccess(personRepository.findByGender(view.getQueryGender()))
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
        }

        subscriber.subscribe({ persons ->
            withView { view -> view.showPersons(persons) }
        }, {
            withView { view -> view.showPersonLoadError() }
        })
    }

    override fun savePerson(person: Person) {
        val subscriber = rxManager.single<Person> { emitter ->
            try {
                personRepository.create(person)
                emitter.onSuccess(person)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        subscriber.subscribe({ p ->
            withView { view -> view.personCreated(p) }
        }, {
            withView { view -> view.showPersonEditError() }
        })
    }

    override fun updatePerson(person: Person) {
        val subscriber = rxManager.single<Person> { emitter ->
            try {
                personRepository.update(person)
                emitter.onSuccess(person)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        subscriber.subscribe({ p ->
            withView { view -> view.personUpdated(p) }
        }, {
            withView { view -> view.showPersonEditError() }
        })
    }

    override fun deletePerson(person: Person) {
        val subscriber = rxManager.single<Person> { emitter ->
            try {
                personRepository.delete(person)
                emitter.onSuccess(person)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        subscriber.subscribe({ p ->
            withView { view -> view.personDeleted(p) }
        }, {
            withView { view -> view.showPersonEditError() }
        })
    }

    private fun withView(todo: (view: DBFlowContract.View) -> Unit) {
        val view = viewRef.get()
        if (view != null) {
            todo(view)
        }
    }
}