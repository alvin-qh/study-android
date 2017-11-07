package alvin.base.kotlin.dbflow.domain.converts

import com.raizlabs.android.dbflow.converter.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConvert : TypeConverter<String, LocalDate>() {

    override fun getModelValue(data: String?): LocalDate? {
        if (data == null) {
            return null
        }
        return LocalDate.from(DateTimeFormatter.ISO_DATE.parse(data))
    }

    override fun getDBValue(model: LocalDate?): String? {
        if (model == null) {
            return null
        }
        return model.format(DateTimeFormatter.ISO_DATE)
    }
}