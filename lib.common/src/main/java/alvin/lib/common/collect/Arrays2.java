package alvin.lib.common.collect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public final class Arrays2 {

    private Arrays2() {
    }

    public static <T> boolean nullOrEmpty(@Nullable T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean notNullOrEmpty(@Nullable String[] actions) {
        return !nullOrEmpty(actions);
    }

    @NonNull
    public static <K, V> Map<K, V> toMap(@NonNull V[] array,
                                         @NonNull Function<? super V, ? extends K> keyMapFn) {
        return Collections2.toMap(Arrays.stream(array), keyMapFn);
    }
}
