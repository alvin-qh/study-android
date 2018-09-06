package alvin.base.database.dbflow.views;

import android.widget.Toast;

import java.time.LocalDate;
import java.util.List;

import alvin.base.database.R;
import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.common.domain.models.IPerson;
import alvin.base.database.common.views.BaseActivity;
import alvin.base.database.dbflow.DBFlowContracts;
import alvin.base.database.dbflow.domain.models.Person;


public class DBFlowActivity extends BaseActivity<DBFlowContracts.Presenter>
        implements DBFlowContracts.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dbflow;
    }

    @Override
    protected int getTitleId() {
        return R.string.title_dbflow;
    }

    @Override
    public void getPersons(Gender gender) {
        presenter.getPersons(gender);
    }

    @Override
    public void savePerson(String name, Gender gender, LocalDate birthday) {
        presenter.savePerson(new Person(name, gender, birthday));
    }

    @Override
    public void updatePerson(IPerson person) {
        presenter.updatePerson(person);
    }

    @Override
    public void deletePerson(IPerson person) {
        presenter.deletePerson(person);
    }

    @Override
    public void onPersonGot(List<IPerson> persons) {
        showPersons(persons);
    }

    @Override
    public void onPersonCreate(IPerson person) {
        final String message = String.format("Persons %s created", person.getName());
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        getPersons();
    }

    @Override
    public void onPersonUpdate(IPerson person) {
        final String message = String.format("Persons %s updated", person.getName());
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        getPersons();
    }

    @Override
    public void onPersonDelete(IPerson person) {
        final String message = String.format("Persons %s deleted", person.getName());
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        getPersons();
    }
}
