package alvin.base.kotlin.dagger.domain.repositories

import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.common.domain.modules.Person_Table
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.kotlinextensions.update
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository
@Inject constructor(
        private val database: DatabaseDefinition
) {
    fun create(p: Person) {
        database.executeTransaction {
            p.save()
        }
    }

    fun update(p: Person) {
        database.executeTransaction {
            p.update()
        }
    }

    fun delete(p: Person) {
        database.executeTransaction {
            p.delete()
        }
    }

    fun findById(id: Int): Optional<Person> {
        val p = SQLite.select().from(Person::class.java)
                .where(Person_Table.id.eq(id))
                .querySingle()
        return Optional.ofNullable(p)
    }

    fun findByGender(gender: Gender?): List<Person> {
        var query = SQLite.select().from(Person::class.java).where()
        if (gender != null) {
            query = query.and(Person_Table.gender.eq(gender))
        }
        return query.queryList()
    }
}