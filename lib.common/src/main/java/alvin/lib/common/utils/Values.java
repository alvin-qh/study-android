package alvin.lib.common.utils;

import android.support.annotation.Nullable;

public final class Values {

    private Values() {
    }

    @Nullable
    public static <T> T safeGet(@Nullable T val, @Nullable T def) {
        return val == null ? def : val;
    }
}
