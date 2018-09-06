package alvin.base.database.common.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import alvin.base.database.R;
import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.common.domain.models.IPerson;
import alvin.base.database.common.views.adapters.PersonAdapter;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity<Presenter extends IPresenter> extends ActivityAdapter<Presenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view_person)
    RecyclerView rvPerson;

    @BindView(R.id.radio_gender)
    RadioGroup radioGender;

    private PersonAdapter adapter;

    private WeakReference<FormDialog> dialogRef = new WeakReference<>(null);

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

        adapter = new PersonAdapter(this, Collections.emptyList());
        adapter.setOnItemEditLListener(item -> {
            FormDialog.Builder builder = new FormDialog.Builder(this);
            FormDialog dlg = builder.create(R.string.title_form_dialog);

            dlg.setOnConfirmClickListener(v -> {
                dialogRef = new WeakReference<>(dlg);
                updatePerson(item.merge(dlg.getName(), dlg.getGender(), dlg.getBirthday()));
            });
            dlg.setName(item.getName());
            dlg.setGender(item.getGender());
            dlg.setBirthday(item.getBirthday());
            dlg.show();
        });

        adapter.setOnItemDeleteLListener(this::deletePerson);

        rvPerson.setAdapter(adapter);
        rvPerson.setItemAnimator(new DefaultItemAnimator());
        rvPerson.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        radioGender.setOnCheckedChangeListener((group, checkedId) -> getPersons());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPersons();
    }

    protected void showPersons(List<IPerson> persons) {
        adapter.update(persons);
        final FormDialog dlg = dialogRef.get();
        if (dlg != null) {
            dlg.dismiss();
            dialogRef.clear();
        }
    }

    protected void getPersons() {
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
        getPersons(gender);
    }

    @OnClick(R.id.fab)
    public void onFloatingActionButtonClick(FloatingActionButton b) {
        FormDialog.Builder builder = new FormDialog.Builder(this);
        FormDialog dlg = builder.create(R.string.title_form_dialog);

        dlg.setOnConfirmClickListener(v -> {
            dialogRef = new WeakReference<>(dlg);
            savePerson(dlg.getName(), dlg.getGender(), dlg.getBirthday());
        });
        dlg.show();
    }

    protected abstract int getLayoutId();

    protected abstract int getTitleId();

    public abstract void getPersons(Gender gender);

    public abstract void savePerson(String name, Gender gender, LocalDate birthday);

    public abstract void updatePerson(IPerson person);

    public abstract void deletePerson(IPerson person);
}
