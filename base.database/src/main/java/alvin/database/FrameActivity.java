package alvin.database;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.time.LocalDate;
import java.util.List;

import alvin.database.models.Gender;
import alvin.database.models.IPerson;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public abstract class FrameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_person)
    ListView listView;

    @BindView(R.id.switch_gender)
    SwitchCompat switchGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(getTitleId());
        }
        showList();
    }

    private void showList() {
        Gender gender = switchGender.isChecked() ? Gender.MALE : Gender.FEMALE;
        List<IPerson> persons = getPersons(gender);
        listView.setAdapter(new PersonAdapter(this, persons));
    }

    protected abstract int getLayoutId();

    protected abstract int getTitleId();

    protected abstract void savePerson(String name, Gender gender, LocalDate birthday);

    protected abstract List<IPerson> getPersons(Gender gender);

    @OnClick(R.id.fab)
    public void onFloatingActionButtonClick(FloatingActionButton button) {
        FormDialog.Builder builder = new FormDialog.Builder(this);

        FormDialog dlg = builder.create(R.string.title_form_dialog);
        dlg.setOnConfirmClickListener(v -> {
            savePerson(dlg.getName(), dlg.getGender(), dlg.getBirthday());
            dlg.dismiss();
            showList();
        });
        dlg.show();
    }

    @OnCheckedChanged(R.id.switch_gender)
    public void onGenderSwitchChanged(SwitchCompat button) {
        showList();
    }
}
