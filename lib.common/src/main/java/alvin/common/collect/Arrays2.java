package alvin.common.collect;

public final class Arrays2 {

    private Arrays2() {
    }

    public static <T> boolean nullOrEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
