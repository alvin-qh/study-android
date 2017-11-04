package alvin.common.collect;

import java.util.Collection;

import javax.annotation.Nullable;

public final class Collections2 {

    private Collections2() {
    }

    public static <T> boolean nullOrEmpty(@Nullable Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
