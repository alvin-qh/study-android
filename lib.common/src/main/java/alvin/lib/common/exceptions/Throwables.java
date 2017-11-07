package alvin.lib.common.exceptions;


import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

public final class Throwables {
    private Throwables() {
    }

    public static ExceptionWrapper propagate(@NonNull Throwable throwable) {
        Preconditions.checkNotNull(throwable, "throwable is null");

        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }
        if (throwable instanceof Error) {
            throw (Error) throwable;
        }
        return new ExceptionWrapper(throwable);
    }
}
