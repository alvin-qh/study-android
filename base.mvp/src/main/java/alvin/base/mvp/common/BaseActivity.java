package alvin.base.mvp.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import alvin.base.mvp.R;
import alvin.base.mvp.domain.models.Message;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static alvin.base.mvp.common.Constract.Presenter;
import static alvin.base.mvp.common.Constract.View;

public abstract class BaseActivity extends AppCompatActivity implements View {

    @BindView(R.id.et_message)
    EditText etMessage;

    @BindView(R.id.rv_message_list)
    RecyclerView rvMessageListView;

    private MessageListAdapter listAdapter;
    private Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

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
    protected void onStart() {
        super.onStart();
        presenter.started();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stoped();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyed();
    }

    @Override
    public Context context() {
        return this;
    }

    @OnClick(R.id.btn_save)
    public void onSaveButtonClick(Button b) {
        presenter.createMessage(etMessage.getText().toString());
    }

    protected abstract Presenter getPresenter();

    protected abstract void inject();
}
