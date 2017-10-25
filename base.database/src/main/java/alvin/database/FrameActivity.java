package alvin.database;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.time.LocalDate;
import java.util.List;

import alvin.database.models.Gender;
import alvin.database.models.IPerson;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class FrameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_person)
    ListView listView;

    @BindView(R.id.radio_gender)
    RadioGroup radioGender;

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

        radioGender.setOnCheckedChangeListener((group, checkedId) -> showList());
    }

    public void showList() {
        Gender gender = null;
        switch (radioGender.getCheckedRadioButtonId()) {
        case R.id.radio_gender_male:
            gender = Gender.MALE;
            break;
        case R.id.radio_gender_female:
            gender = Gender.FEMALE;
            break;
        }
        List<IPerson> persons = getPersons(gender);
        listView.setAdapter(new PersonAdapter(this, persons));
    }

    protected abstract int getLayoutId();

    protected abstract int getTitleId();

    protected abstract void savePerson(String name, Gender gender, LocalDate birthday);

    protected abstract List<IPerson> getPersons(Gender gender);

    protected abstract void updatePerson(int id, String name, Gender gender, LocalDate birthday);

    protected abstract void deletePerson(int id);

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
}
