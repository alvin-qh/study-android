package alvin.kotlin.dbflow.domain.repositories

import alvin.kotlin.dbflow.domain.MainDatabase
import alvin.kotlin.dbflow.domain.models.Gender
import alvin.kotlin.dbflow.domain.models.Person
import alvin.kotlin.dbflow.domain.models.Person_Table
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.kotlinextensions.update
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.util.*

class PersonRepository(
        private val database: DatabaseDefinition = FlowManager.getDatabase(MainDatabase::class.java)
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

    fun findByGender(gender: Gender): List<Person> {
        return SQLite.select().from(Person::class.java)
                .where(Person_Table.gender.eq(gender))
                .queryList()
    }
}