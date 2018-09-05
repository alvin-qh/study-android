package alvin.adv.mvp.department.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import alvin.adv.mvp.R;
import alvin.adv.mvp.domain.models.Department;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepartmentListAdapter
        extends RecyclerView.Adapter<DepartmentListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Department> departments;

    private OnDeleteDepartmentListener deleteDepartmentListener;

    DepartmentListAdapter(@NonNull Context context,
                          @NonNull List<Department> departments) {
        this.inflater = LayoutInflater.from(context);
        this.departments = departments;
    }

    void update(@NonNull List<Department> departments) {
        this.departments = departments;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.department_view_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Department department = departments.get(position);
        holder.tvDepartmentName.setText(department.getName());
        holder.departmentId = department.getId();
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    void setDeleteDepartmentListener(OnDeleteDepartmentListener listener) {
        this.deleteDepartmentListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_department_name) TextView tvDepartmentName;

        private int departmentId;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.btn_delete)
        void onDeleteButtonClick(ImageButton b) {
            if (deleteDepartmentListener != null && departmentId > 0) {
                deleteDepartmentListener.delete(departmentId);
            }
        }
    }

    public interface OnDeleteDepartmentListener {
        void delete(int departmentId);
    }
}
