package alvin.common.collect;

import javax.annotation.Nullable;

public final class Arrays2 {

    private Arrays2() {
    }

    public static <T> boolean nullOrEmpty(@Nullable T[] array) {
        return array == null || array.length == 0;
    }
}
