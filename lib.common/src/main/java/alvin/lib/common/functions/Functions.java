package alvin.lib.common.functions;

import java.util.function.Consumer;

public final class Functions {

    private static final Consumer EMPTY_CONSUMER = o -> {
    };

    private Functions() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> emptyConsumer() {
        return (Consumer<T>) EMPTY_CONSUMER;
    }
}
