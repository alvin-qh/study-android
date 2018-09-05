package alvin.adv.dagger.utils;

import android.support.annotation.NonNull;

public final class ObjectNames {

    private ObjectNames() {
    }

    @NonNull
    public static String name(@NonNull Object obj) {
        return obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
    }

    @NonNull
    public static String simpleName(@NonNull Object obj) {
        final String name = name(obj);
        final int index = name.lastIndexOf('.');
        if (index < 0) {
            return name;
        }
        return name.substring(index + 1);
    }
}
