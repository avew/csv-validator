package io.github.avew;

import java.util.Objects;

@FunctionalInterface
public interface CsvConsumer<T> {

    void accept(T t) throws Exception;

    default CsvConsumer<T> andThen(CsvConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

}
