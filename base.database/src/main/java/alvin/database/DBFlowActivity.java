package alvin.database;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import alvin.database.dbflow.FlowDatabase;
import alvin.database.dbflow.models.Person;
import alvin.database.dbflow.repositories.PersonDBFlowRepository;
import alvin.database.models.Gender;
import alvin.database.models.IPerson;


public class DBFlowActivity extends FrameActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dbflow;
    }

    @Override
    protected int getTitleId() {
        return R.string.title_dbflow;
    }

    @Override
    protected void savePerson(String name, Gender gender, LocalDate birthday) {
        Person person = new Person(name, gender, birthday);
        DatabaseDefinition database = FlowManager.getDatabase(FlowDatabase.class);
        PersonDBFlowRepository repository = new PersonDBFlowRepository(database);
        repository.save(person);
    }

    @Override
    protected List<IPerson> getPersons(Gender gender) {
        DatabaseDefinition database = FlowManager.getDatabase(FlowDatabase.class);
        PersonDBFlowRepository repository = new PersonDBFlowRepository(database);
        return Collections.unmodifiableList(repository.findByGender(gender));
    }
}
