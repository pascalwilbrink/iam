create table reset_password_tokens (
    id INTEGER PRIMARY KEY NOT NULL,
    token VARCHAR(255) NOT NULL,
    account_id INTEGER NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    consumed_at TIMESTAMP,
    version INTEGER NOT NULL DEFAULT 0,
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    updated TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by INTEGER,
    updated_by INTEGER
);
