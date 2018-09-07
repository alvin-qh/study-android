package alvin.base.kotlin.common.views

import alvin.base.kotlin.R
import alvin.base.kotlin.common.domain.models.Gender
import alvin.base.kotlin.common.domain.models.Person
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_person.view.*
import java.time.format.DateTimeFormatter

class PersonListAdapter
constructor(
        context: Context,
        private var persons: List<Person>)
    : RecyclerView.Adapter<PersonListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    var onItemEditListener: ((person: Person) -> Unit)? = null

    var onItemDeleteListener: ((person: Person) -> Unit)? = null

    fun update(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = inflater.inflate(R.layout.view_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        val view = holder.view

        view.tv_id.text = person.id.toString()
        view.tv_name.text = person.name
        view.tv_gender.text = when (person.gender) {
            Gender.MALE -> view.context.getString(R.string.label_gender_male)
            Gender.FEMALE -> view.context.getString(R.string.label_gender_female)
            else -> ""
        }
        view.tv_birthday.text =
                if (person.birthday == null) ""
                else person.birthday?.format(DateTimeFormatter.ISO_DATE)

        view.btn_edit.setOnClickListener { _ -> onItemEditListener?.invoke(person) }
        view.btn_delete.setOnClickListener { _ -> onItemDeleteListener?.invoke(person) }
    }

    override fun getItemCount(): Int {
        return this.persons.size
    }

    data class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
