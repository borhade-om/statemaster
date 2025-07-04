CREATE TABLE state (
    state_id BIGINT GENERATED  BY DEFAULT AS IDENTITY PRIMARY KEY ,
    state_name VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    update_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    state_status VARCHAR(20)
);