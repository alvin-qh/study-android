package alvin.lib.common.collect;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

public final class Collections2 {

    private Collections2() {
    }

    public static <T> boolean nullOrEmpty(@Nullable Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    @NonNull
    public static <K, V> Map<K, V> toMap(@NonNull Stream<V> stream,
                                         @NonNull Function<? super V, ? extends K> keyMapFn) {
        return stream.collect(Collectors.toMap(keyMapFn, Function.identity(), (a, b) -> b));
    }

    @NonNull
    public static <K, V> Map<K, V> toMap(@NonNull Collection<V> collection,
                                         @NonNull Function<? super V, ? extends K> keyMapFn) {
        return toMap(collection.stream(), keyMapFn);
    }
}
