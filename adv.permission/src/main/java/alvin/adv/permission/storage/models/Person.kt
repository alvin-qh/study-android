package alvin.adv.permission.storage.models

import alvin.lib.mvp.contracts.IModel
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

class Person
@JsonCreator constructor(
        @JsonProperty("name") val name: String,
        @JsonProperty("gender") val gender: Gender,
        @JsonProperty("birthday") val birthday: LocalDate,
        @JsonProperty("remark") val remark: String
) : IModel
