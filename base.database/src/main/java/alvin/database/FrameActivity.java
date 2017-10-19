package alvin.database;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.List;

import alvin.database.domain.models.Person;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class FrameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_person)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(getTitleId());
        }
        showList();
    }

    private void showList() {
        List<Person> persons = getPersons();
        listView.setAdapter(new PersonAdapter(this, persons));
    }

    protected abstract int getLayoutId();

    protected abstract int getTitleId();

    protected abstract void savePerson(Person person);

    protected abstract List<Person> getPersons();

    @OnClick(R.id.fab)
    public void onFloatingActionButtonClick(FloatingActionButton button) {
        FormDialog.Builder builder = new FormDialog.Builder(this);

        FormDialog dlg = builder.create(R.string.title_form_dialog);
        dlg.setOnConfirmClickListener(v -> {
            savePerson(dlg.getPerson());
            dlg.dismiss();
            showList();
        });
        dlg.show();
    }
}
