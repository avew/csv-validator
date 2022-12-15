package io.github.avew.reader;

import io.github.avew.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public abstract class CsvReader<T extends CsvValue> extends CsvUtil {

    public abstract CsvResultReader<T> read(InputStream is);

    protected CsvResultReader<T> read(
            InputStream is,
            String[] typeHeader,
            String delimeter,
            CsvValuesSerializer<T> serializer
    ) {
        CsvResultReader<T> result = new CsvResultReader<T>();
        Set<ValidationCsvDTO> validations = new HashSet<>();

        final Class<T> type = getParameterType();
        boolean hasCtorWithNoParam = Arrays.stream(type.getDeclaredConstructors())
                .anyMatch(e -> e.getParameterCount() == 0);

        if (!hasCtorWithNoParam) {
            throw new IllegalArgumentException("Unable to construct csv value (" + type.getCanonicalName() + "), require constructor with 0 param");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8));

        try {
            String[] contentHeader = getHeader(br.readLine(), delimeter);
            ValidationCsvDTO headerValidation = headerValidation(typeHeader, contentHeader);

            if (headerValidation.isError()) {
                validations.add(headerValidation);
                result.setValidations(validations);
                result.setError(true);
                return result;
            }

            String lineContent;
            AtomicInteger index = new AtomicInteger(1);
            List<T> values = new ArrayList<>();

            while ((lineContent = br.readLine()) != null) {
                String[] x = lineContent.split(";", -1);
                int line = index.getAndIncrement();

                T value = type.getDeclaredConstructor().newInstance();
                value.setLine(line);
                value.setRaw(List.of(x));

                if (x.length > typeHeader.length) {
                    validations.add(ValidationCsvDTO.builder()
                            .line(value.getLine())
                            .error(true)
                            .message("the number of columns is not the same as the header")
                            .build());
                    continue;
                }

                try {
                    // all column, bukan kyk di emet external yang ngelakuin iterasi per-kolom
                    serializer.apply(value.getLine(), x, validations, value);
                    values.add(value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    validations.add(ValidationCsvDTO.builder()
                            .line(value.getLine())
                            .error(true)
                            .message(ex.getMessage())
                            .build());
                }
            }

            result.setValues(values);
            result.setCount(values.size());

            if (validations.size() > 0) {
                result.setError(true);
                result.setValidations(validations.stream().sorted(Comparator.comparing(ValidationCsvDTO::getLine)).collect(Collectors.toList()));
            }
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getParameterType() {
        ParameterizedType param = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) param.getActualTypeArguments()[0];
    }

}
