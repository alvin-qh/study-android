package alvin.base.dagger.basic.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.dagger.R;
import alvin.base.dagger.basic.domain.models.Message;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static alvin.base.dagger.basic.BasicContracts.Presenter;
import static alvin.base.dagger.basic.BasicContracts.View;


@Singleton
public class BasicActivity extends ActivityAdapter<Presenter> implements View {
    private static final String TAG = BasicActivity.class.getSimpleName();

    @Inject
    Presenter presenter;

    @BindView(R.id.et_message)
    EditText etMessage;

    @BindView(R.id.rv_message_list)
    RecyclerView rvMessageListView;

    private MessageListAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic);

        ButterKnife.bind(this);

        listAdapter = new MessageListAdapter(this, presenter);
        rvMessageListView.setAdapter(listAdapter);
        rvMessageListView.setItemAnimator(new DefaultItemAnimator());
        rvMessageListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showMessages(@NonNull List<Message> messageList) {
        this.listAdapter.update(messageList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.btn_save)
    public void onSaveButtonClick(Button b) {
        presenter.createMessage(etMessage.getText().toString());
    }

    @Override
    public void showException(@NonNull Throwable error) {
        Log.e(TAG, "Exception caused", error);
        Toast.makeText(this, R.string.error_exception, Toast.LENGTH_LONG).show();
    }
}
