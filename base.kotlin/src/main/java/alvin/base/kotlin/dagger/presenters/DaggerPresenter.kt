package alvin.base.kotlin.dagger.presenters

import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.dagger.DaggerContracts
import alvin.base.kotlin.dagger.domain.repositories.PersonRepository
import alvin.base.kotlin.lib.common.rx.RxManager
import alvin.lib.mvp.ViewPresenterAdapter
import javax.inject.Inject

class DaggerPresenter
@Inject constructor(view: DaggerContracts.View,
                    private val personRepository: PersonRepository,
                    private val rxManager: RxManager) :
        ViewPresenterAdapter<DaggerContracts.View>(view),
        DaggerContracts.Presenter {

    override fun onStart() {
        super.onStart()
        reloadPersons()
    }

    override fun onDestroy() {
        super.onDestroy()
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
        }, { throwable ->
            withView { view -> view.showException(throwable) }
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
        }, { throwable ->
            withView { view -> view.showException(throwable) }
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
        }, { throwable ->
            withView { view -> view.showException(throwable) }
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
        }, { throwable ->
            withView { view -> view.showException(throwable) }
        })
    }
}
