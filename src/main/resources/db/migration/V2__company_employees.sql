CREATE TABLE IF NOT EXISTS rateme.company_employees (

    company_id BIGINT
        CONSTRAINT fk__companies__id REFERENCES rateme.companies (id),
    user_id    BIGINT
        CONSTRAINT fk__users__id REFERENCES rateme.users (id)
);

ALTER TABLE rateme.company_employees
    DROP CONSTRAINT IF EXISTS unique_company_employee;
ALTER TABLE rateme.company_employees
    ADD CONSTRAINT unique_company_employee UNIQUE (company_id, user_id);
