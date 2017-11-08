package alvin.lib.mvp;

import android.content.Context;
import android.widget.Toast;

public interface IView {
    Context context();

    default void showDefaultError(Throwable t) {
        Toast.makeText(context(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
