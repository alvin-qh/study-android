package alvin.ui.layout.merge.domain.models

enum class Gender(private val value: String) {
    MALE("Mr"), FEMALE("Miss");

    val title: String
        get() {
            return value
        }
}
