package alvin.database.sqlite.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import alvin.database.sqlite.models.Gender;
import alvin.database.sqlite.models.Person;


public class PersonSQLiteRepository {
    private static final String TABLE_NAME = "user";

    private final SQLiteDatabase database;

    public PersonSQLiteRepository(SQLiteDatabase database) {
        this.database = database;
    }

    public List<Person> findAll() {
        Cursor cursor = database.rawQuery("select id,name,gender,birthday from " + TABLE_NAME, null);
        try {
            List<Person> persons = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

                Gender gender = null;
                String rawData = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
                if (!Strings.isNullOrEmpty(rawData)) {
                    gender = Gender.valueOf(rawData);
                }

                rawData = cursor.getString(cursor.getColumnIndexOrThrow("birthday"));
                LocalDate birthday = null;
                if (!Strings.isNullOrEmpty(rawData)) {
                    birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(rawData));
                }

                persons.add(new Person(id, name, gender, birthday));
            }
            return persons;
        } finally {
            cursor.close();
        }
    }

    public void create(Person person) {
        ContentValues cv = new ContentValues();
        cv.put("name", person.getName() == null ? "" : person.getName());
        cv.put("gender", person.getGender() == null ? "" : person.getGender().toString());
        cv.put("birthday", person.getBirthday() == null ? "" : person.getBirthday().format(DateTimeFormatter.ISO_DATE));

        database.beginTransaction();
        try {
            database.insertOrThrow(TABLE_NAME, null, cv);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }
}
