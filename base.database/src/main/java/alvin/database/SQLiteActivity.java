package alvin.database;

import java.util.List;

import alvin.database.domain.models.Person;
import alvin.database.domain.repositories.PersonSQLiteRepository;
import alvin.database.sqlite.SQLite;

public class SQLiteActivity extends FrameActivity {

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
        try (SQLite sqlite = new SQLite(this, "sqlite_db")) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getWritable());
            repository.create(person);
        }
    }

    @Override
    protected List<Person> getPersons() {
        try (SQLite sqlite = new SQLite(this, "sqlite_db")) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getReadable());
            return repository.findAll();
        }
    }
}
