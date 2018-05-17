create table clients (
    id INTEGER PRIMARY KEY NOT NULL,
    client_id VARCHAR(255) UNIQUE NOT NULL,
    client_secret VARCHAR(255) NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(999),
    version INTEGER NOT NULL DEFAULT 0,
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    updated TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by INTEGER,
    updated_by INTEGER
);

