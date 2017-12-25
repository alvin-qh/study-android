package alvin.base.kotlin.dbflow.domain.services

import alvin.base.kotlin.common.domain.db.MainDatabase
import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.dbflow.domain.repositories.PersonRepository
import alvin.lib.common.dbflow.repositories.TransactionManager
import com.raizlabs.android.dbflow.config.FlowManager

class PersonService {
    private val repository = PersonRepository()
    private val txManager = TransactionManager(FlowManager.getDatabase(MainDatabase::class.java))

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
