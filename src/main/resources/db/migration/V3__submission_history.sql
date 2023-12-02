CREATE TABLE IF NOT EXISTS rateme.submissions (
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT              NOT NULL
        CONSTRAINT fk__users__id REFERENCES rateme.users (id),
    task_id        BIGINT              NOT NULL
        CONSTRAINT fk__tasks__id REFERENCES rateme.tasks (id),
    global_token    VARCHAR(255)        NOT NULL,
    token          VARCHAR(255) UNIQUE NOT NULL,
    test_id        BIGINT              NOT NULL,
    status         VARCHAR(255)        NOT NULL,
    stdout         TEXT,
    compile_output TEXT,
    time           DOUBLE PRECISION,
    memory         DOUBLE PRECISION,
    language_id    BIGINT              NOT NULL,
    created        TIMESTAMP           NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx__submissions__user_id ON rateme.submissions (user_id);
CREATE INDEX IF NOT EXISTS idx__submissions__task_id ON rateme.submissions (task_id);
CREATE INDEX IF NOT EXISTS idx__submissions__token ON rateme.submissions (token);
CREATE INDEX IF NOT EXISTS idx__submissions__global_token ON rateme.submissions (global_token);
