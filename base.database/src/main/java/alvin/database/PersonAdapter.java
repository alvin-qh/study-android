package alvin.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import alvin.database.sqlite.models.Person;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final List<Person> persons;

    public PersonAdapter(Context context, List<Person> persons) {
        this.mInflater = LayoutInflater.from(context);
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Person getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_person, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Person item = getItem(position);

        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        holder.gender.setText(item.getGender().toString());
        holder.birthday.setText(item.getBirthday().format(DateTimeFormatter.ISO_DATE));
        return view;
    }

    public static class ViewHolder {
        @BindView(R.id.id)
        TextView id;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.gender)
        TextView gender;

        @BindView(R.id.birthday)
        TextView birthday;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
