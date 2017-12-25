package alvin.base.kotlin.dagger.domain.services

import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
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
    fun findByGender(gender: Gender?): List<Person> {
        return repository.findByGender(gender)
    }

    fun create(person: Person) {
        txManager.begin().use {
            person.save()
            it.commit()
        }
    }

    fun update(person: Person) {
        txManager.begin().use {
            person.update()
            it.commit()
        }
    }

    fun delete(person: Person) {
        txManager.begin().use {
            person.delete()
            it.commit()
        }
    }
}
