-- Initialize MySQL database for Credit Application
-- This script is executed after MySQL container starts

-- Grant privileges to credit_user on credit_db
GRANT ALL PRIVILEGES ON credit_db.* TO 'credit_user'@'%';
FLUSH PRIVILEGES;


