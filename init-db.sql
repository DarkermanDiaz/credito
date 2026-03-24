-- Grant privileges to the credit_user on credit_db
-- This script is executed by postgres user, after credit_db is created via POSTGRES_DB env var
GRANT ALL PRIVILEGES ON DATABASE credit_db TO credit_user;
GRANT USAGE ON SCHEMA public TO credit_user;
GRANT CREATE ON SCHEMA public TO credit_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO credit_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO credit_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO credit_user;


