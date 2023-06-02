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
public abstract class CsvewReader<T extends CsvewValue> extends Csvew {

    public abstract CsvewResultReader<T> process(int startAt, InputStream is);

    @SuppressWarnings({"SameParameterValue", "SpellCheckingInspection"})
    protected CsvewResultReader<T> read(
            int startAt,
            InputStream is,
            String[] typeHeader,
            String delimeter,
            CsvewValuesSerializer<T> serializer
    ) {
        CsvewResultReader<T> result = new CsvewResultReader<T>();
        Set<CsvewValidationDTO> validations = new HashSet<>();

        final Class<T> type = getParameterType();
        boolean hasCtorWithNoParam = Arrays.stream(type.getDeclaredConstructors())
                .anyMatch(e -> e.getParameterCount() == 0);

        if (!hasCtorWithNoParam) {
            throw new IllegalArgumentException("Unable to construct csv value (" + type.getCanonicalName() + "), require constructor with 0 param");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8));

        try {
            String[] contentHeader = getHeader(br.readLine(), delimeter);
            CsvewValidationDTO headerValidation = headerValidation(typeHeader, contentHeader);

            if (headerValidation.isError()) {
                validations.add(headerValidation);
                result.setValidations(validations);
                result.setError(true);
                return result;
            }

            List<T> values = new ArrayList<>();
            String lineContent;

            if (startAt == 0 || startAt == 1) {
                startAt = 1;
                log.info("START LINE, AT={}", startAt);
            } else {
                log.info("SKIP LINE, CURRENT READ AT={}", startAt);
            }
            AtomicInteger index = new AtomicInteger(startAt);
            for (int x = 1; x < startAt; x++) {
                br.readLine();
            }
            while ((lineContent = br.readLine()) != null) {
                String[] x = lineContent.split(delimeter, -1);
                int line = index.getAndIncrement();

                T value = type.getDeclaredConstructor().newInstance();
                value.setLine(line);
                value.setRaw(List.of(x));

                if (x.length > typeHeader.length) {
                    validations.add(CsvewValidationDTO.builder()
                            .line(value.getLine())
                            .error(true)
                            .message("the number of columns is not the same as the header")
                            .build());
                    continue;
                }

                try {
                    serializer.apply(value.getLine(), x, validations, value);
                    values.add(value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    validations.add(CsvewValidationDTO.builder()
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
                result.setValidations(validations.stream().sorted(Comparator.comparing(CsvewValidationDTO::getLine)).collect(Collectors.toList()));
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
