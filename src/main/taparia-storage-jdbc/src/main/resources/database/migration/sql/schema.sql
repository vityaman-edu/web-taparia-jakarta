--liquibase formatted sql


--changeset vityaman:init
CREATE SCHEMA taparia;

CREATE SEQUENCE taparia.seq_user_account_id
START 1 INCREMENT 1;

CREATE TABLE taparia.user_account (
    id              BIGINT          PRIMARY KEY
    DEFAULT nextval('taparia.seq_user_account_id'),
    login           VARCHAR(64)     NOT NULL UNIQUE,
    password_hash   BYTEA           NOT NULL,
    password_salt   BYTEA           NOT NULL
);

CREATE SEQUENCE taparia.seq_picture_id
START 1 INCREMENT 1;

CREATE TABLE taparia.picture (
    id              BIGINT          PRIMARY KEY
    DEFAULT nextval('taparia.seq_picture_id'),
    owner_id        BIGINT          NOT NULL,
    name            VARCHAR(128)    NOT NULL,
    data            TEXT            NOT NULL,

    CONSTRAINT uq_owner_id_and_name
    UNIQUE (owner_id, name),

    CONSTRAINT fk_user_account
    FOREIGN KEY (owner_id)
    REFERENCES taparia.user_account (id)
);

CREATE TYPE taparia.permission_user_picture_type
AS ENUM ('READ', 'DELETE');

CREATE TABLE taparia.permission_user_picture (
    owner_id        BIGINT                                  NOT NULL,
    type            taparia.permission_user_picture_type    NOT NULL,
    picture_id      BIGINT                                  NOT NULL,

    PRIMARY KEY (owner_id, picture_id),

    CONSTRAINT fk_user_account
    FOREIGN KEY (owner_id)
    REFERENCES taparia.user_account (id),

    CONSTRAINT fk_picture
    FOREIGN KEY (picture_id)
    REFERENCES taparia.picture (id)
);


--changeset vityaman:tap_results_support
CREATE TYPE taparia.tap_result_status
AS ENUM ('HIT', 'MISS');

CREATE SEQUENCE taparia.seq_tap_result_id
START 1 INCREMENT 1;

CREATE TABLE taparia.tap_result (
    id              BIGINT                      PRIMARY KEY
    DEFAULT nextval('taparia.seq_tap_result_id'),
    owner_id        BIGINT                      NOT NULL,
    picture_id      BIGINT                      NOT NULL,
    x               BIGINT                      NOT NULL,
    y               BIGINT                      NOT NULL,
    status          taparia.tap_result_status   NOT NULL,

    CONSTRAINT fk_user_account
    FOREIGN KEY (owner_id)
    REFERENCES taparia.user_account (id),

    CONSTRAINT fk_picture
    FOREIGN KEY (picture_id)
    REFERENCES taparia.picture (id)
);

--changeset vityaman:access_token_support
CREATE TABLE taparia.access_token (
    user_id         BIGINT          PRIMARY KEY,
    token           VARCHAR(32)     NOT NULL,
    created_at      TIMESTAMP       NOT NULL,
    ttl             INTERVAL        NOT NULL,

    CONSTRAINT fk_user_account
    FOREIGN KEY (user_id)
    REFERENCES taparia.user_account (id)
);
