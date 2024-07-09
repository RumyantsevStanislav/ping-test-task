package ru.test.ping.utils;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import ru.test.ping.entities.Execution;
import ru.test.ping.repositories.specifications.ExecutionSpecification;

import java.time.OffsetDateTime;
import java.util.Map;

import static ru.test.ping.utils.Consts.RequestParams.*;

/**
 * Фильтр операций по заданным параметрам.
 */
@Getter
public class ExecutionsFilter {
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
            OffsetDateTime from = OffsetDateTime.parse(requestParams.get(FROM));
            executionSpecification = executionSpecification.and(ExecutionSpecification.executedAtGreaterOrEqualsThen(from));
        }
        if (isParamExist(requestParams, TO)) {
            OffsetDateTime to = OffsetDateTime.parse(requestParams.get(TO));
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
}
