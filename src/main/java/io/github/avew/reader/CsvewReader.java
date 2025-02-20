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

    public abstract CsvewResultReader<T> process(int start, InputStream is);

    protected CsvewResultReader<T> read(
            InputStream is,
            String[] typeHeader,
            String delimeter,
            CsvewValuesSerializer<T> serializer
    ) {
        return read(1, is, typeHeader, delimeter, serializer);
    }

    protected CsvewResultReader<T> read(
            InputStream is,
            String[] typeHeader,
            CsvewValuesSerializer<T> serializer
    ) {
        return read(1, is, typeHeader, ";", serializer);
    }

    protected CsvewResultReader<T> read(
            int startAt,
            InputStream is,
            String[] typeHeader,
            CsvewValuesSerializer<T> serializer
    ) {
        return read(startAt, is, typeHeader, ";", serializer);
    }

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

            /* dont validate header */
//            if (!skipHeader) {
            String[] contentHeader = getHeader(br.readLine(), delimeter);
            CsvewValidationDTO headerValidation = headerValidation(typeHeader, contentHeader, delimeter);

            if (headerValidation.isError()) {
                validations.add(headerValidation);
                result.setValidations(validations);
                result.setError(true);
                return result;
            }
//            }


            List<T> values = new ArrayList<>();
            String lineContent;


            if (startAt == 0 || startAt == 1) {
                startAt = 1;
                log.debug("READ LINE {}", startAt);
            } else log.debug("SKIP LINE CURRENT READ {}", startAt);

            AtomicInteger index = new AtomicInteger(startAt);
            for (int x = 1; x < startAt; x++) br.readLine();

            while ((lineContent = br.readLine()) != null) {
                String[] x = lineContent.split(delimeter, -1);
                int line = index.getAndIncrement();

                T value = type.getDeclaredConstructor().newInstance();
                value.setLine(line);
                value.setRaw(List.of(x));

//                if (!skipHeader) {
                if (x.length > typeHeader.length) {
                    validations.add(CsvewValidationDTO.builder()
                            .line(value.getLine())
                            .error(true)
                            .message("the number of columns is not the same as the header")
                            .lineContent(lineContent)
                            .build());
                    continue;
                }
//                }


                try {
                    int currentValidationsSize = validations.size();

                    serializer.apply(value.getLine(), x, validations, value);
                    values.add(value);

                    int updatedValidationsSize = validations.size();
                    if (updatedValidationsSize > currentValidationsSize) modifyLineContent(line, lineContent, validations);
                } catch (Exception ex) {
                    log.error("error apply serializer parse csv {}", ex.getMessage());
                    validations.add(CsvewValidationDTO.builder()
                            .line(value.getLine())
                            .error(true)
                            .message(ex.getMessage())
                            .lineContent(lineContent)
                            .build());
                }
            }

            result.setValues(values);
            result.setCount(values.size());


            if (!validations.isEmpty()) {
                result.setError(true);
                result.setValidations(validations.stream().sorted(Comparator.comparing(CsvewValidationDTO::getLine)).collect(Collectors.toList()));
            }
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException ex) {
            log.error("error parse csv {}", ex.getMessage());
        }

        return result;
    }

    @SuppressWarnings({"SameParameterValue", "SpellCheckingInspection"})
    protected CsvewResultReader<T> read(
        int startAt,
        InputStream is,
        String[] typeHeader,
        String delimeter,
        CsvewValuesSerializer<T> serializer,
        boolean skipHeader
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

            /* validate header */
            if (!skipHeader) {
                String[] contentHeader = getHeader(br.readLine(), delimeter);
                CsvewValidationDTO headerValidation = headerValidation(typeHeader, contentHeader, delimeter);

                if (headerValidation.isError()) {
                    validations.add(headerValidation);
                    result.setValidations(validations);
                    result.setError(true);
                    return result;
                }
            }

            List<T> values = new ArrayList<>();
            String lineContent;


            if (startAt == 0 || startAt == 1) {
                startAt = 1;
                log.debug("READ LINE {}", startAt);
            } else log.debug("SKIP LINE CURRENT READ {}", startAt);

            AtomicInteger index = new AtomicInteger(startAt);
            for (int x = 1; x < startAt; x++) br.readLine();

            while ((lineContent = br.readLine()) != null) {
                String[] x = lineContent.split(delimeter, -1);
                int line = index.getAndIncrement();

                T value = type.getDeclaredConstructor().newInstance();
                value.setLine(line);
                value.setRaw(List.of(x));

                if (!skipHeader) {
                    if (x.length > typeHeader.length) {
                        validations.add(CsvewValidationDTO.builder()
                            .line(value.getLine())
                            .error(true)
                            .message("the number of columns is not the same as the header")
                            .lineContent(lineContent)
                            .build());
                        continue;
                    }
                }

                try {
                    int currentValidationsSize = validations.size();

                    serializer.apply(value.getLine(), x, validations, value);
                    values.add(value);

                    int updatedValidationsSize = validations.size();
                    if (updatedValidationsSize > currentValidationsSize) modifyLineContent(line, lineContent, validations);
                } catch (Exception ex) {
                    log.error("error apply serializer parse csv {}", ex.getMessage());
                    validations.add(CsvewValidationDTO.builder()
                        .line(value.getLine())
                        .error(true)
                        .message(ex.getMessage())
                        .lineContent(lineContent)
                        .build());
                }
            }

            result.setValues(values);
            result.setCount(values.size());

            if (!validations.isEmpty()) {
                result.setError(true);
                result.setValidations(validations.stream().sorted(Comparator.comparing(CsvewValidationDTO::getLine)).collect(Collectors.toList()));
            }
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException ex) {
            log.error("error parse csv {}", ex.getMessage());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getParameterType() {
        ParameterizedType param = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) param.getActualTypeArguments()[0];
    }

    protected Set<CsvewValidationDTO> modifyLineContent(int line, String lineContent, Set<CsvewValidationDTO> validations) {
        for (CsvewValidationDTO validation : validations) {
            if (validation.getLine() == line) {
                validation.setLineContent(lineContent);
                return validations;
            }
        }

        return validations;
    }

}
