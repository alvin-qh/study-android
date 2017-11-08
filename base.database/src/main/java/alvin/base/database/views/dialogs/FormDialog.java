package alvin.base.database.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import alvin.base.database.R;
import alvin.base.database.models.Gender;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormDialog extends Dialog {

    @BindView(R.id.text_name)
    EditText editName;

    @BindView(R.id.radio_gender)
    RadioGroup radioGender;

    @BindView(R.id.text_birthday)
    EditText editBirthday;

    private View.OnClickListener onConfirmClickListener;

    FormDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_person, null);
        setContentView(view);

        ButterKnife.bind(this);
        editBirthday.setKeyListener(null);
    }

    @OnClick(R.id.btn_calendar)
    void onCalendarButtonClick(ImageButton b) {
        DatePickerDialog dialog;

        String date = editBirthday.getText().toString();
        if (Strings.isNullOrEmpty(date)) {
            dialog = new DatePickerDialog(getContext());
        } else {
            LocalDate birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(date));
            dialog = new DatePickerDialog(getContext(), null,
                    birthday.getYear(), birthday.getMonthValue() - 1, birthday.getDayOfMonth());
        }
        dialog.setOnDateSetListener((view, year, month, dayOfMonth) ->
                editBirthday.setText(LocalDate.of(year, month + 1, dayOfMonth).format(DateTimeFormatter.ISO_DATE)));
        dialog.show();
    }

    @OnClick(R.id.btn_confirm)
    void onConfirmClick(Button b) {
        if (onConfirmClickListener != null) {
            onConfirmClickListener.onClick(b);
        }
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick(Button b) {
        this.dismiss();
    }

    public void setOnConfirmClickListener(View.OnClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public String getName() {
        return editName.getText().toString();
    }

    public void setName(String name) {
        editName.setText(name == null ? "" : name);
    }

    public Gender getGender() {
        Gender gender = null;
        switch (radioGender.getCheckedRadioButtonId()) {
        case R.id.radio_gender_male:
            gender = Gender.MALE;
            break;
        case R.id.radio_gender_female:
            gender = Gender.FEMALE;
            break;
        }
        return gender;
    }

    public void setGender(Gender gender) {
        switch (gender) {
        case MALE:
            radioGender.check(R.id.radio_gender_male);
            break;
        case FEMALE:
            radioGender.check(R.id.radio_gender_female);
            break;
        }
    }

    public LocalDate getBirthday() {
        LocalDate birthday = null;
        if (editBirthday.getText().length() > 0) {
            birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(editBirthday.getText().toString()));
        }
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday == null) {
            editBirthday.setText("");
        } else {
            editBirthday.setText(birthday.format(DateTimeFormatter.ISO_DATE));
        }
    }

    public static class Builder {

        private final Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public FormDialog create(int titleResId) {
            FormDialog dlg = new FormDialog(context, R.style.AppTheme_Dialog);
            dlg.setTitle(context.getString(titleResId));
            dlg.create();
            return dlg;
        }
    }
}
