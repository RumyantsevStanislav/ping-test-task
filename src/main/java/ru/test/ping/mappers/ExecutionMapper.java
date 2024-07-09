package ru.test.ping.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.test.ping.entities.Execution;
import ru.test.ping.entities.dtos.ExecutionDto;

/**
 * Маппер сущности {@link Execution}
 */
@Mapper
public interface ExecutionMapper {
    /**
     * Переменная для доступа к мапперу.
     */
    ExecutionMapper EXECUTION_MAPPER = Mappers.getMapper(ExecutionMapper.class);

    /**
     * Маппинг сущности {@link Execution} на {@link ExecutionDto}
     *
     * @param execution источник данных.
     * @return DTO.
     */
    ExecutionDto toDto(Execution execution);
}
