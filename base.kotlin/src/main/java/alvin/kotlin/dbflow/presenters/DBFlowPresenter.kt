package alvin.kotlin.dbflow.presenters

import alvin.common.rx.RxSchedulers
import alvin.kotlin.dbflow.DBFlowContract
import alvin.kotlin.dbflow.domain.models.Person
import alvin.kotlin.dbflow.domain.repositories.PersonRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

class DBFlowPresenter(val view: DBFlowContract.View) : DBFlowContract.Presenter {

    private val personRepository = PersonRepository()

    private val viewRef = WeakReference<DBFlowContract.View>(view)

    private var disposable: Disposable? = null

    override fun doCreated() {
    }

    override fun doStarted() {
        reloadPersons()
    }

    override fun doDestroyed() {
        disposable?.dispose()
    }

    override fun reloadPersons() {
        disposable = Single
                .create { emitter: SingleEmitter<List<Person>> ->
                    withView { view ->
                        try {
                            emitter.onSuccess(personRepository.findByGender(view.getQueryGender()))
                        } catch (e: Exception) {
                            emitter.onError(e)
                        }
                    }
                }
                .subscribeWith(null)
                .subscribeOn(RxSchedulers.database())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ persons ->
                    withView { view -> view.showPersons(persons) }
                }, {
                    withView { view -> view.showPersonLoadError() }
                })
    }

    override fun savePerson(person: Person) {
        disposable = Single
                .create { emitter: SingleEmitter<Person> ->
                    try {
                        personRepository.create(person)
                        emitter.onSuccess(person)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        emitter.onError(e)
                    }
                }
                .subscribeOn(RxSchedulers.database())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ p ->
                    withView { view -> view.personCreated(p) }
                }, {
                    withView { view -> view.showPersonCreateError() }
                })
    }

    override fun updatePerson(person: Person) {
        disposable = Single
                .create { emitter: SingleEmitter<Person> ->
                    try {
                        personRepository.update(person)
                        emitter.onSuccess(person)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        emitter.onError(e)
                    }
                }
                .subscribeOn(RxSchedulers.database())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ p ->
                    withView { view -> view.personUpdated(p) }
                }, {
                    withView { view -> view.showPersonCreateError() }
                })
    }

    override fun deletePerson(person: Person) {

    }

    private fun withView(todo: (view: DBFlowContract.View) -> Unit) {
        val view = viewRef.get()
        if (view != null) {
            todo(view)
        }
    }
}