create table executions
(
    id               uuid primary key,
    address          varchar not null,
    execution_state  varchar(255) not null,
    executed_at      timestamp,
    execution_result varchar
);