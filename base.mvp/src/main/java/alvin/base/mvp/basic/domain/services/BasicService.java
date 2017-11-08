package alvin.base.mvp.basic.domain.services;

import android.content.Context;

import javax.inject.Inject;

import alvin.base.mvp.R;

public class BasicService {

    private final Context context;

    @Inject
    public BasicService(Context context) {
        this.context = context;
    }

    public String getMessage() {
        return context.getString(R.string.message_basic);
    }
}
