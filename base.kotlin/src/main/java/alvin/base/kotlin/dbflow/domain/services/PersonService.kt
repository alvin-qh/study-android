package alvin.base.kotlin.dbflow.domain.services

import alvin.base.kotlin.common.domain.db.MainDatabase
import alvin.base.kotlin.common.domain.models.Gender
import alvin.base.kotlin.common.domain.models.Person
import alvin.base.kotlin.dbflow.domain.repositories.PersonRepository
import alvin.lib.common.dbflow.repositories.TransactionManager
import com.raizlabs.android.dbflow.config.FlowManager

class PersonService {
    private val repository = PersonRepository()
    private val txManager = TransactionManager(FlowManager.getDatabase(MainDatabase::class.java))

    fun findByGender(gender: Gender?, callback: (List<Person>) -> Unit) {
        repository.findByGender(gender, callback)
    }

    fun create(person: Person, callback: () -> Unit) {
        txManager.executeAsync { person.save() }
                .success { callback() }
                .build()
                .execute()
    }

    fun update(person: Person, callback: () -> Unit) {
        txManager.executeAsync { person.update() }
                .success { callback() }
                .build()
                .execute()
    }

    fun delete(person: Person, callback: () -> Unit) {
        txManager.executeAsync { person.delete() }
                .success { callback() }
                .build()
                .execute()
    }
}
