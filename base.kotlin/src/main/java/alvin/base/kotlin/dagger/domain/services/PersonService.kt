package alvin.base.kotlin.dagger.domain.services

import alvin.base.kotlin.common.domain.models.Gender
import alvin.base.kotlin.common.domain.models.Person
import alvin.base.kotlin.dagger.domain.repositories.PersonRepository
import alvin.lib.common.dbflow.repositories.TransactionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonService
@Inject constructor(
        private val repository: PersonRepository,
        private val txManager: TransactionManager
) {
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
