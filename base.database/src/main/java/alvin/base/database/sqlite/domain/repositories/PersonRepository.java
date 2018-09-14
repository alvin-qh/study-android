package alvin.base.database.sqlite.domain.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.common.domain.models.IPerson;
import alvin.base.database.sqlite.domain.SQLite;
import alvin.base.database.sqlite.domain.models.Person;

@Singleton
public class PersonRepository {
    private static final String TABLE_NAME = "user";

    private final SQLite.Builder sqliteBuilder;

    @Inject
    PersonRepository(SQLite.Builder sqliteBuilder) {
        this.sqliteBuilder = sqliteBuilder;
    }

    public List<IPerson> findByGender(Gender gender) {
        try (SQLite sqlite = sqliteBuilder.build()) {
            return sqlite.query(db -> {
                final String[] columns = {"id", "name", "gender", "birthday"};

                String selection = "1=1 ";
                List<String> args = new ArrayList<>();

                if (gender != null) {
                    selection += "and gender=? ";
                    args.add(gender.toString());
                }

                List<IPerson> persons = new ArrayList<>();
                try (Cursor c = db.query(TABLE_NAME, columns, selection,
                        args.toArray(new String[args.size()]), null, null, null)) {

                    while (c.moveToNext()) {
                        int id = c.getInt(c.getColumnIndexOrThrow("id"));
                        String name = c.getString(c.getColumnIndexOrThrow("name"));

                        Gender _gender = null;
                        String rawData = c.getString(c.getColumnIndexOrThrow("gender"));
                        if (!Strings.isNullOrEmpty(rawData)) {
                            _gender = Gender.valueOf(rawData);
                        }

                        rawData = c.getString(c.getColumnIndexOrThrow("birthday"));
                        LocalDate birthday = null;
                        if (!Strings.isNullOrEmpty(rawData)) {
                            birthday = LocalDate.parse(rawData);
                        }

                        persons.add(new Person(id, name, _gender, birthday));
                    }
                    return persons;
                }
            });
        }
    }

    public void create(IPerson person) {
        try (SQLite sqlite = sqliteBuilder.build()) {
            sqlite.execute(db -> {
                ContentValues cv = new ContentValues();
                cv.put("name", person.getName() == null ? "" : person.getName());
                cv.put("gender", person.getGender() == null ? "" : person.getGender().toString());
                cv.put("birthday", person.getBirthday() == null ? null : person.getBirthday().toString());

                db.insertOrThrow(TABLE_NAME, null, cv);
            });
        }
    }

    public void update(IPerson person) {
        try (SQLite sqlite = sqliteBuilder.build()) {
            sqlite.execute(db -> {
                ContentValues cv = new ContentValues();
                cv.put("name", person.getName() == null ? "" : person.getName());
                cv.put("gender", person.getGender() == null ? "" : person.getGender().toString());
                cv.put("birthday", person.getBirthday() == null ? null : person.getBirthday().toString());

                String[] args = {String.valueOf(person.getId())};
                db.update(TABLE_NAME, cv, "id=?", args);
            });
        }
    }

    public void delete(int id) {
        try (SQLite sqlite = sqliteBuilder.build()) {
            sqlite.execute(db -> {
                String[] args = {String.valueOf(id)};
                db.delete(TABLE_NAME, "id=?", args);
            });
        }
    }
}
