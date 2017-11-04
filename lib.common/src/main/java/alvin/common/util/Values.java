package alvin.common.util;

import android.support.annotation.Nullable;

public final class Values {

    @Nullable
    public static <T> T safeGet(@Nullable T val, @Nullable T def) {
        return val == null ? def : val;
    }
}
