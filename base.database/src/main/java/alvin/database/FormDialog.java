package alvin.database;

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

import alvin.database.models.Gender;
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

    public FormDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_form, null);
        setContentView(view);

        ButterKnife.bind(this);

        editBirthday.setKeyListener(null);
    }

    @OnClick(R.id.btn_calendar)
    public void onCalendarButtonClick(ImageButton button) {
        DatePickerDialog dialog;

        String date = editBirthday.getText().toString();
        if (Strings.isNullOrEmpty(date)) {
            dialog = new DatePickerDialog(getContext(), 0);
        } else {
            LocalDate birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(date));
            dialog = new DatePickerDialog(getContext(), 0, null,
                    birthday.getYear(), birthday.getMonthValue() - 1, birthday.getDayOfMonth());
        }
        dialog.setOnDateSetListener((view, year, month, dayOfMonth) ->
                editBirthday.setText(LocalDate.of(year, month, dayOfMonth).format(DateTimeFormatter.ISO_DATE)));
        dialog.show();
    }

    @OnClick(R.id.btn_confirm)
    public void onConfirmClick(Button button) {
        if (onConfirmClickListener != null) {
            onConfirmClickListener.onClick(button);
        }
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelClick(Button button) {
        this.dismiss();
    }

    public void setOnConfirmClickListener(View.OnClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public String getName() {
        return editName.getText().toString();
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

    public LocalDate getBirthday() {
        LocalDate birthday = null;
        if (editBirthday.getText().length() > 0) {
            birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(editBirthday.getText().toString()));
        }
        return birthday;
    }

    public static class Builder {

        private final Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public FormDialog create(int titleResId) {
            FormDialog dlg = new FormDialog(context, R.style.AppTheme_Dialog);
            dlg.setTitle(context.getString(titleResId));
            return dlg;
        }
    }
}
