package alvin.common.collect;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

public final class Arrays2 {

    private Arrays2() {
    }

    @NonNull
    public static <T> boolean nullOrEmpty(@Nullable T[] array) {
        return array == null || array.length == 0;
    }

    @NonNull
    public static <K, V> Map<K, V> toMap(@NonNull V[] array,
                                         @NonNull Function<? super V, ? extends K> keyMapFn) {
        return Collections2.toMap(Arrays.stream(array), keyMapFn);
    }
}
