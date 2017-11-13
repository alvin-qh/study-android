package alvin.base.kotlin.common.domain.modules

import alvin.base.kotlin.common.domain.db.MainDatabase
import alvin.lib.common.dbflow.converts.LocalDateConvert
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import java.time.LocalDate

@Table(name = "person", database = MainDatabase::class)
data class Person(
        @PrimaryKey(autoincrement = true)
        var id: Int = 0,

        @Column(name = "name")
        var name: String? = null,

        @Column(name = "gender")
        var gender: Gender? = null,

        @Column(name = "birthday", typeConverter = LocalDateConvert::class)
        var birthday: LocalDate? = null
) {
    constructor(
            name: String?,
            gender: Gender?,
            birthday: LocalDate?
    ) : this(0, name, gender, birthday)
}