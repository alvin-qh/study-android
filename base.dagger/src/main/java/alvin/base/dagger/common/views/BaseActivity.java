package alvin.base.dagger.common.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import alvin.base.dagger.R;
import alvin.base.dagger.common.contracts.CommonContracts;
import alvin.base.dagger.common.domain.models.Message;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity
        extends AppCompatActivity
        implements CommonContracts.View {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.et_message)
    EditText etMessage;

    @BindView(R.id.rv_message_list)
    RecyclerView rvMessageListView;

    private MessageListAdapter listAdapter;
    private CommonContracts.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.common_activity_base);

        ButterKnife.bind(this);

        inject();

        presenter = getPresenter();

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

    protected abstract CommonContracts.Presenter getPresenter();

    protected abstract void inject();
}
