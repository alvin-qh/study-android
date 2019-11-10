package alvin.base.preferences.storebox.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.common.base.Strings;

import net.orange_box.storebox.StoreBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import alvin.base.preferences.R;
import alvin.base.preferences.common.domain.models.Gender;
import alvin.base.preferences.storebox.domain.models.Person;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreBoxActivity extends AppCompatActivity {

    @BindView(R.id.text_name)
    EditText editName;

    @BindView(R.id.radio_gender)
    RadioGroup radioGender;

    @BindView(R.id.text_birthday)
    EditText editBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storebox);

        ButterKnife.bind(this);

        // Make EditText readonly
        editBirthday.setKeyListener(null);

        initializeData();
    }

    private void initializeData() {
        Person person = StoreBox.create(this, Person.class);
        editName.setText(person.getName());

        Gender gender = person.getGender();
        if (gender == null) {
            radioGender.check(R.id.radio_gender_male);
        } else {
            switch (gender) {
            case MALE:
                radioGender.check(R.id.radio_gender_male);
                break;
            case FEMALE:
                radioGender.check(R.id.radio_gender_female);
                break;
            default:
                break;
            }
        }

        LocalDate birthday = person.getBirthday();
        if (birthday != null) {
            editBirthday.setText(person.getBirthday().format(DateTimeFormatter.ISO_DATE));
        }
    }

    @OnClick(R.id.btn_calendar)
    public void onCalendarClick(ImageButton button) {
        DatePickerDialog dialog;

        String date = editBirthday.getText().toString();
        if (Strings.isNullOrEmpty(date)) {
            dialog = new DatePickerDialog(this, 0);
        } else {
            LocalDate birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(date));
            dialog = new DatePickerDialog(this, 0, null,
                    birthday.getYear(), birthday.getMonthValue() - 1, birthday.getDayOfMonth());
        }
        dialog.setOnDateSetListener((view, year, month, dayOfMonth) ->
                editBirthday.setText(LocalDate.of(year, month, dayOfMonth).format(DateTimeFormatter.ISO_DATE)));
        dialog.show();
    }

    @OnClick(R.id.btn_save)
    public void onSaveClick(Button elem) {
        Person person = StoreBox.create(this, Person.class);
        person.setName(editName.getText().toString());

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
        person.setGender(gender);

        LocalDate birthday = null;
        String date = editBirthday.getText().toString();
        if (!Strings.isNullOrEmpty(date)) {
            try {
                birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(date));
            } catch (DateTimeParseException e) {
                Toast.makeText(this, getString(R.string.error_error_date_format), Toast.LENGTH_LONG).show();
            }
        }
        person.setBirthday(birthday);

        Toast.makeText(this, getString(R.string.message_success), Toast.LENGTH_LONG).show();
    }
}
