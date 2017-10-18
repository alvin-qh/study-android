package alvin.preferences;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import alvin.preferences.domain.models.Gender;
import alvin.preferences.domain.models.Person;
import alvin.preferences.domain.repositories.PersonRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OriginalActivity extends AppCompatActivity {

    @BindView(R.id.text_name)
    EditText editName;

    @BindView(R.id.radio_gender)
    RadioGroup radioGender;

    @BindView(R.id.text_birthday)
    EditText editBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_original);

        ButterKnife.bind(this);

        // Make EditText readonly
        editBirthday.setKeyListener(null);

        initializeData();
    }

    private void initializeData() {
        PersonRepository repository = new PersonRepository(this);
        Person person = repository.load();

        editName.setText(person.getName());

        switch (person.getGender()) {
        case MALE:
            radioGender.check(R.id.radio_gender_male);
            break;
        case FEMALE:
            radioGender.check(R.id.radio_gender_female);
            break;
        default:
            radioGender.check(R.id.radio_gender_male);
        }

        ZoneId zoneId = ZoneId.systemDefault();
        editBirthday.setText(SimpleDateFormat.getDateInstance().format(Date.from(person.getBirthday().atStartOfDay().atZone(zoneId).toInstant())));
    }

    @OnClick(R.id.btn_calendar)
    public void onCalendarClick(ImageButton button) {
        DatePickerDialog dialog = new DatePickerDialog(this, 0);
        dialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            final String date = SimpleDateFormat.getDateInstance().format(calendar.getTime());
            editBirthday.setText(date);
        });
        dialog.show();
    }

    @OnClick(R.id.btn_save)
    public void onSaveClick(Button elem) {
        String name = editName.getText().toString();

        Gender gender;
        switch (radioGender.getCheckedRadioButtonId()) {
        case R.id.radio_gender_male:
            gender = Gender.MALE;
            break;
        case R.id.radio_gender_female:
            gender = Gender.FEMALE;
            break;
        default:
            gender = Gender.MALE;
            break;
        }

        LocalDate birthday = null;
        String date = editBirthday.getText().toString();
        if (!Strings.isNullOrEmpty(date)) {
            try {
                ZoneId zoneId = ZoneId.systemDefault();
                birthday = LocalDateTime.ofInstant(SimpleDateFormat.getDateInstance().parse(date).toInstant(), zoneId).toLocalDate();
            } catch (ParseException e) {
                Toast.makeText(this, getString(R.string.error_error_date_format), Toast.LENGTH_LONG).show();
            }
        }

        PersonRepository repository = new PersonRepository(this);
        repository.save(new Person(name, gender, birthday));

        Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_LONG).show();
    }
}
