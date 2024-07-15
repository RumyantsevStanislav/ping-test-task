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
    /**
     * Строка запроса с применяемым фильтром.
     */
    private StringBuilder filterDefinition;

    public ExecutionsFilter(@NonNull Map<String, String> requestParams) {
        this.executionSpecification = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if (isParamExist(requestParams, ADDRESS)) {
            String queryParamValue = requestParams.get(ADDRESS);
            executionSpecification = executionSpecification.and(ExecutionSpecification.addressLike(queryParamValue));
            filterDefinition.append(appendQueryParam(ADDRESS, queryParamValue));
        }
        if (isParamExist(requestParams, STATE)) {
            String queryParamValue = requestParams.get(STATE);
            Execution.ExecutionState state = Execution.ExecutionState.valueOf(queryParamValue);
            executionSpecification = executionSpecification.and(ExecutionSpecification.stateIs(state));
            filterDefinition.append(appendQueryParam(STATE, queryParamValue));
        }
        if (isParamExist(requestParams, FROM)) {
            String queryParamValue = requestParams.get(FROM);
            OffsetDateTime from = convertToOffsetDateTime(queryParamValue);
            executionSpecification = executionSpecification.and(ExecutionSpecification.executedAtGreaterOrEqualsThen(from));
            filterDefinition.append(appendQueryParam(FROM, queryParamValue));
        }
        if (isParamExist(requestParams, TO)) {
            String queryParamValue = requestParams.get(TO);
            OffsetDateTime to = convertToOffsetDateTime(queryParamValue);
            executionSpecification = executionSpecification.and(ExecutionSpecification.executedAtLesserOrEqualsThen(to));
            filterDefinition.append(appendQueryParam(TO, queryParamValue));
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

    private String appendQueryParam(String queryParamName, String queryParamValue) {
        return "&" + queryParamName + "=" + queryParamValue;
    }
}
