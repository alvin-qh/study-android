package alvin.adv.permission.storage

import alvin.adv.permission.storage.models.Person
import alvin.lib.mvp.contracts.IPresenter
import alvin.lib.mvp.contracts.IView

object StorageContracts {

    interface View : IView {
        fun saveComplete()
        fun saveFailed()
        fun showPerson(person: Person)
        fun loadFailed()
    }

    interface Presenter : IPresenter {
        fun savePerson(person: Person)
        fun loadPerson()
    }
}
