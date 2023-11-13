CREATE SCHEMA IF NOT EXISTS rateme;

CREATE TABLE IF NOT EXISTS rateme.companies (
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(256) UNIQUE NOT NULL,
    email    VARCHAR(256)        NOT NULL,
    password VARCHAR(256)        NOT NULL,
    enabled  BOOLEAN             NOT NULL DEFAULT FALSE,
    created  TIMESTAMP WITH TIME ZONE     DEFAULT NOW(),
    modified TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rateme.contests (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(256) NOT NULL,
    company_id BIGINT
        CONSTRAINT fk__company_id REFERENCES rateme.companies (id),
    created    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    modified   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rateme.tasks (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    picture_key   VARCHAR(256),
    description   TEXT         NOT NULL,
    difficulty    VARCHAR(16)  NOT NULL,
    input_format  TEXT,
    output_format TEXT,
    input_data    jsonb        NOT NULL,
    output_data   jsonb        NOT NULL,
    code_example  TEXT,
    contest_id    BIGINT
        CONSTRAINT fk__contest_id REFERENCES rateme.contests (id),
    created       TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    modified      TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx__companies_name ON rateme.companies (name);
CREATE INDEX IF NOT EXISTS idx__contests__company_id ON rateme.contests (company_id);
CREATE INDEX IF NOT EXISTS idx__contests__name ON rateme.contests (name);
CREATE INDEX IF NOT EXISTS idx__tasks__contest_id ON rateme.tasks (contest_id);
CREATE INDEX IF NOT EXISTS idx__tasks__name ON rateme.tasks (name);

CREATE TABLE IF NOT EXISTS rateme.users (
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(256) UNIQUE NOT NULL,
    password VARCHAR(256)        NOT NULL
);

CREATE TABLE IF NOT EXISTS rateme.roles (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS rateme.user_role (
    user_id BIGINT
        CONSTRAINT fk__users__id REFERENCES rateme.users (id),
    role_id BIGINT
        CONSTRAINT fk__roles__id REFERENCES rateme.roles (id)
);

ALTER TABLE rateme.user_role
    DROP CONSTRAINT IF EXISTS unique__role_user;
ALTER TABLE rateme.user_role
    ADD CONSTRAINT unique__role_user UNIQUE (user_id, role_id);
