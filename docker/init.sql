-- This script runs when the PostgreSQL container is first created
-- The 'matches' database is already created by POSTGRES_DB environment variable

-- Connect to the matches database
\c matches;

-- Create any initial tables or data here if needed
-- For example:
-- CREATE TABLE IF NOT EXISTS example (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(255)
-- );

-- Grant all privileges on the database to the postgres user (already has them by default)
GRANT ALL PRIVILEGES ON DATABASE matches TO postgres;

-- You can add more initialization SQL here
