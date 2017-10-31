package alvin.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import alvin.database.models.IPerson;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final List<IPerson> persons;
    private final FrameActivity activity;

    public PersonAdapter(FrameActivity activity, List<IPerson> persons) {
        this.activity = activity;
        this.mInflater = LayoutInflater.from(activity);
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public IPerson getItem(int position) {
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
            view = mInflater.inflate(R.layout.listview_person, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        IPerson item = getItem(position);

        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        holder.gender.setText(item.getGender().toString());
        holder.birthday.setText(item.getBirthday() == null ? "" :
                item.getBirthday().format(DateTimeFormatter.ISO_DATE));

        holder.edit.setOnClickListener(v1 -> {
            FormDialog.Builder builder = new FormDialog.Builder(activity);

            FormDialog dlg = builder.create(R.string.title_form_dialog);
            dlg.setOnConfirmClickListener(v2 -> {
                activity.updatePerson(item.getId(), dlg.getName(), dlg.getGender(), dlg.getBirthday());
                dlg.dismiss();

                activity.showList();
            });
            dlg.show();

            dlg.setName(item.getName());
            dlg.setGender(item.getGender());
            dlg.setBirthday(item.getBirthday());
        });

        holder.delete.setOnClickListener(v -> {
            activity.deletePerson(item.getId());
            activity.showList();
        });

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

        @BindView(R.id.btn_edit)
        ImageButton edit;

        @BindView(R.id.btn_delete)
        ImageButton delete;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
