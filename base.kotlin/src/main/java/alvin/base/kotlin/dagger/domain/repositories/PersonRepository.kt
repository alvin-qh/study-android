@file:Suppress("unused")

package alvin.base.kotlin.dagger.domain.repositories

import alvin.base.kotlin.common.domain.models.Gender
import alvin.base.kotlin.common.domain.models.Person
import alvin.base.kotlin.common.domain.models.Person_Table
import alvin.lib.common.dbflow.repositories.BaseRepository
import com.raizlabs.android.dbflow.sql.language.SQLite
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository
@Inject constructor() : BaseRepository<Person>() {

    fun findById(id: Int, callback: (Person?) -> Unit) {
        SQLite.select().from(Person::class.java)
                .where(Person_Table.id.eq(id))
                .async()
                .querySingleResultCallback { _, person -> callback(person) }
                .execute()
    }

    fun findByGender(gender: Gender?, callback: (List<Person>) -> Unit) {
        var query = SQLite.select().from(Person::class.java).where()
        if (gender != null) {
            query = query.and(Person_Table.gender.eq(gender))
        }
        query.async()
                .queryListResultCallback { _, persons -> callback(persons) }
                .execute()
    }
}
