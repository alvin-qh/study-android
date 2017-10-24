package alvin.database;

import java.util.List;

import alvin.database.sqlite.models.Person;
import alvin.database.sqlite.repositories.PersonSQLiteRepository;
import alvin.database.sqlite.SQLite;

public class DBFlowActivity extends FrameActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sqlite;
    }

    @Override
    protected int getTitleId() {
        return R.string.title_sqlite;
    }

    @Override
    protected void savePerson(Person person) {
        try (SQLite sqlite = SQLite.createSQLiteDB(this)) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getWritable());
            repository.create(person);
        }
    }

    @Override
    protected List<Person> getPersons() {
        try (SQLite sqlite = SQLite.createSQLiteDB(this)) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getReadable());
            return repository.findAll();
        }
    }
}
