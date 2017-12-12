package alvin.lib.common.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class SystemServices {
    private final Context context;
    private InputMethodManager inputMethodManager;

    public SystemServices(Context context) {
        this.context = context;
    }

    public InputMethodManager getInputMethodManager() {
        if (inputMethodManager == null) {
            inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return inputMethodManager;
    }
}
