package alvin.base.database.common.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import alvin.base.database.R;
import alvin.base.database.common.domain.models.IPerson;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private List<IPerson> persons;

    private OnItemEditLListener onItemEditLListener;
    private OnItemDeleteLListener onItemDeleteLListener;

    public PersonAdapter(@NonNull Context context, @NonNull List<IPerson> persons) {
        this.inflater = LayoutInflater.from(context);
        this.persons = persons;
    }

    public void update(@NonNull List<IPerson> persons) {
        this.persons = persons;
        this.notifyDataSetChanged();
    }

    public void setOnItemEditLListener(OnItemEditLListener onItemEditLListener) {
        this.onItemEditLListener = onItemEditLListener;
    }

    public void setOnItemDeleteLListener(OnItemDeleteLListener onItemDeleteLListener) {
        this.onItemDeleteLListener = onItemDeleteLListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.common_view_person_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final IPerson item = persons.get(position);

        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        holder.gender.setText(item.getGender().toString());
        holder.birthday.setText(item.getBirthday() == null ? "" :
                item.getBirthday().format(DateTimeFormatter.ISO_DATE));

        holder.edit.setOnClickListener(view -> {
            if (onItemEditLListener != null) {
                onItemEditLListener.onEdit(item);
            }
        });

        holder.delete.setOnClickListener(view -> {
            if (onItemDeleteLListener != null) {
                onItemDeleteLListener.onDelete(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemEditLListener {
        void onEdit(IPerson item);
    }

    public interface OnItemDeleteLListener {
        void onDelete(IPerson item);
    }
}
