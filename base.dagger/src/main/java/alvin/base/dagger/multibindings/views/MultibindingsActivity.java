package alvin.base.dagger.multibindings.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import alvin.base.dagger.R;
import alvin.base.dagger.multibindings.MultibindingsContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MultibindingsActivity extends AppCompatActivity implements MultibindingsContracts.View {

    @Inject MultibindingsContracts.Presenter presenter;

    @BindView(R.id.tv_name_set) TextView tvNameSet;

    @BindView(R.id.ll_table) TableLayout tableLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multibindings_activity);

        ButterKnife.bind(this);

        AndroidInjection.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.onStart();
    }

    public void showNameSet(Set<String> names) {
        tvNameSet.setText(names.toString());
    }

    public void showBirthdayMap(Map<String, LocalDate> birthdayMap) {
        final LayoutInflater inflater = getLayoutInflater();

        final Iterator<Map.Entry<String, LocalDate>> iter = birthdayMap.entrySet().iterator();
        for (int i = 0; iter.hasNext(); i++) {
            Map.Entry<String, LocalDate> entry = iter.next();

            int layout = i % 2 == 0 ?
                    R.layout.multibindings_template_table_row_even :
                    R.layout.multibindings_template_table_row_odd;

            TableRow row = (TableRow) inflater.inflate(layout, tableLayout, false);

            TableRowView rowView = new TableRowView(row);
            rowView.tvMapKey.setText(entry.getKey());
            rowView.tvMapValue.setText(entry.getValue().format(DateTimeFormatter.ISO_DATE));

            tableLayout.addView(row);
        }
    }

    class TableRowView {
        @BindView(R.id.tv_map_key) TextView tvMapKey;
        @BindView(R.id.tv_map_value) TextView tvMapValue;

        TableRowView(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
