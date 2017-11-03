package alvin.common.collect;

import java.util.Collection;

public final class Collections2 {

    private Collections2() {
    }

    public static <T> boolean nullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
