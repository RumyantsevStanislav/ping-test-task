package ru.test.ping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.test.ping.entities.Execution;

import java.util.UUID;

/**
 * Репозиторий для доступа к записям о доменах.
 */
@Repository
public interface ExecutionRepository extends JpaRepository<Execution, UUID>, JpaSpecificationExecutor<Execution> {
}
