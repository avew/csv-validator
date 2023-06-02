package io.github.avew;

import java.util.Objects;

@FunctionalInterface
public interface CsvewConsumer<T> {

    void accept(T t) throws Exception;

    default CsvewConsumer<T> andThen(CsvewConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

}
