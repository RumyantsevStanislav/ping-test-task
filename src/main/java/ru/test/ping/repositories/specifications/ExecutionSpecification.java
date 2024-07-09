package ru.test.ping.repositories.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.test.ping.entities.Execution;

import java.time.OffsetDateTime;

/**
 * Фильтры для сущности {@link Execution}
 */
@UtilityClass
public class ExecutionSpecification {
    public static Specification<Execution> addressLike(String address) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("address")), String.format("%%%s%%", address).toUpperCase());
    }

    public static Specification<Execution> stateIs(Execution.ExecutionState state) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("executionState"), state);
    }

    public static Specification<Execution> executedAtGreaterOrEqualsThen(OffsetDateTime from) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("executedAt"), from);
    }

    public static Specification<Execution> executedAtLesserOrEqualsThen(OffsetDateTime to) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("executedAt"), to);
    }
}
