package alvin.adv.database.dbflow.views;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import alvin.adv.database.R;
import alvin.adv.database.common.domain.models.Gender;
import alvin.adv.database.common.domain.models.IPerson;
import alvin.adv.database.common.views.BaseActivity;
import alvin.adv.database.dbflow.domain.FlowDatabase;
import alvin.adv.database.dbflow.domain.models.Person;
import alvin.adv.database.dbflow.domain.repositories.PersonDBFlowRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;


public class DBFlowActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.dbflow_activity;
    }

    @Override
    protected int getTitleId() {
        return R.string.title_dbflow;
    }

    @Override
    public void savePerson(String name, Gender gender, LocalDate birthday) {
        TransactionManager manager = new TransactionManager(
                FlowManager.getDatabase(FlowDatabase.class));
        manager.executeTransaction(db -> new Person(name, gender, birthday).save());
    }

    @Override
    public List<IPerson> getPersons(Gender gender) {
        PersonDBFlowRepository repository = new PersonDBFlowRepository();
        return Collections.unmodifiableList(repository.findByGender(gender));
    }

    @Override
    public void updatePerson(int id, String name, Gender gender, LocalDate birthday) {
        TransactionManager manager = new TransactionManager(
                FlowManager.getDatabase(FlowDatabase.class));

        PersonDBFlowRepository repository = new PersonDBFlowRepository();

        Optional<Person> mayPerson = repository.findById(id);
        mayPerson.ifPresent(person -> {
            person.setName(name);
            person.setGender(gender);
            person.setBirthday(birthday);
            manager.executeTransaction(db -> person.update());
        });
    }

    @Override
    public void deletePerson(int id) {
        TransactionManager manager = new TransactionManager(
                FlowManager.getDatabase(FlowDatabase.class));

        PersonDBFlowRepository repository = new PersonDBFlowRepository();
        Optional<Person> mayPerson = repository.findById(id);

        mayPerson.ifPresent(person -> manager.executeTransaction(db -> person.delete()));
    }
}
