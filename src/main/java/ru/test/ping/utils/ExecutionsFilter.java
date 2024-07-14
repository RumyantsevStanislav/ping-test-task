package ru.test.ping.utils;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import ru.test.ping.entities.Execution;
import ru.test.ping.repositories.specifications.ExecutionSpecification;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ru.test.ping.utils.Consts.MOSCOW_TIME_ZONE;
import static ru.test.ping.utils.Consts.RequestParams.*;

/**
 * Фильтр операций по заданным параметрам.
 */
@Getter
public class ExecutionsFilter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm");
    /**
     * Спецификация запроса.
     */
    private Specification<Execution> executionSpecification;

    public ExecutionsFilter(@NonNull Map<String, String> requestParams) {
        this.executionSpecification = Specification.where(null);
        if (isParamExist(requestParams, ADDRESS)) {
            executionSpecification = executionSpecification.and(ExecutionSpecification.addressLike(requestParams.get(ADDRESS)));
        }
        if (isParamExist(requestParams, STATE)) {
            Execution.ExecutionState state = Execution.ExecutionState.valueOf(requestParams.get(STATE));
            executionSpecification = executionSpecification.and(ExecutionSpecification.stateIs(state));
        }
        if (isParamExist(requestParams, FROM)) {
            OffsetDateTime from = convertToOffsetDateTime(requestParams.get(FROM));
            executionSpecification = executionSpecification.and(ExecutionSpecification.executedAtGreaterOrEqualsThen(from));
        }
        if (isParamExist(requestParams, TO)) {
            OffsetDateTime to = convertToOffsetDateTime(requestParams.get(TO));
            executionSpecification = executionSpecification.and(ExecutionSpecification.executedAtLesserOrEqualsThen(to));
        }
    }

    /**
     * Проверка на существование непустого значения параметра запроса.
     *
     * @param requestParams параметры запроса.
     * @param paramName     заданный параметрзапроса.
     */
    private boolean isParamExist(@NonNull Map<String, String> requestParams, String paramName) {
        return requestParams.containsKey(paramName) && !requestParams.get(paramName).isEmpty();
    }

    private OffsetDateTime convertToOffsetDateTime(String stringLocalDateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(stringLocalDateTime, formatter);
        return OffsetDateTime.of(localDateTime, ZoneId.of(MOSCOW_TIME_ZONE).getRules().getOffset(localDateTime));
    }
}
