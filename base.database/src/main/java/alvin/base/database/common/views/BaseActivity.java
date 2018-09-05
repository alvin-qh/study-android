package alvin.adv.database.common.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import java.time.LocalDate;
import java.util.List;

import alvin.adv.database.R;
import alvin.adv.database.common.domain.models.Gender;
import alvin.adv.database.common.domain.models.IPerson;
import alvin.adv.database.common.views.adapters.PersonAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view_person)
    RecyclerView rvPerson;

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

        final PersonAdapter adapter = createPersonAdapter();
        rvPerson.setAdapter(adapter);
        rvPerson.setItemAnimator(new DefaultItemAnimator());
        rvPerson.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        radioGender.setOnCheckedChangeListener(
                (group, checkedId) -> adapter.update(getPersons()));
    }

    private PersonAdapter createPersonAdapter() {
        PersonAdapter adapter = new PersonAdapter(this, getPersons());

        adapter.setOnItemEditLListener(item -> {
            FormDialog.Builder builder = new FormDialog.Builder(this);

            FormDialog dlg = builder.create(R.string.title_form_dialog);
            dlg.setOnConfirmClickListener(v -> {
                updatePerson(item.getId(), dlg.getName(), dlg.getGender(), dlg.getBirthday());
                adapter.update(getPersons());
                dlg.dismiss();
            });
            dlg.setName(item.getName());
            dlg.setGender(item.getGender());
            dlg.setBirthday(item.getBirthday());

            dlg.show();
        });

        adapter.setOnItemDeleteLListener(item -> {
            deletePerson(item.getId());
            adapter.update(getPersons());
        });

        return adapter;
    }

    private List<IPerson> getPersons() {
        Gender gender;
        switch (radioGender.getCheckedRadioButtonId()) {
        case R.id.radio_gender_male:
            gender = Gender.MALE;
            break;
        case R.id.radio_gender_female:
            gender = Gender.FEMALE;
            break;
        default:
            gender = null;
            break;
        }
        return getPersons(gender);
    }

    protected abstract int getLayoutId();

    protected abstract int getTitleId();

    public abstract List<IPerson> getPersons(Gender gender);

    public abstract void savePerson(String name, Gender gender, LocalDate birthday);

    public abstract void updatePerson(int id, String name, Gender gender, LocalDate birthday);

    public abstract void deletePerson(int id);

    @OnClick(R.id.fab)
    public void onFloatingActionButtonClick(FloatingActionButton b) {
        FormDialog.Builder builder = new FormDialog.Builder(this);

        FormDialog dlg = builder.create(R.string.title_form_dialog);
        dlg.setOnConfirmClickListener(v -> {
            savePerson(dlg.getName(), dlg.getGender(), dlg.getBirthday());
            PersonAdapter adapter = (PersonAdapter) rvPerson.getAdapter();
            adapter.update(getPersons());
            dlg.dismiss();
        });
        dlg.show();
    }
}
