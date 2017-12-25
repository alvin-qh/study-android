package alvin.base.kotlin.dbflow.domain.repositories

import alvin.base.kotlin.common.domain.modules.Gender
import alvin.base.kotlin.common.domain.modules.Person
import alvin.base.kotlin.common.domain.modules.Person_Table
import com.raizlabs.android.dbflow.sql.language.SQLite
import java.util.*

class PersonRepository {

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
