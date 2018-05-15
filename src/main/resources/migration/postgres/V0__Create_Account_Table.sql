create table accounts (
    id INTEGER PRIMARY KEY NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    updated TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by INTEGER,
    updated_by INTEGER
);

