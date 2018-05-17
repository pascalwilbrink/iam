create table account_passwords (
    id INTEGER PRIMARY KEY NOT NULL,
    account_id INTEGER NOT NULL,
    password VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    version INTEGER NOT NULL DEFAULT 0,
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    updated TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by INTEGER,
    updated_by INTEGER
);
