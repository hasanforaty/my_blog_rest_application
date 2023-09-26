-- Drop user first if they exist
DROP USER if exists 'blog_app_admin'@'%' ;

-- Now create user with prop privileges
CREATE USER 'blog_app_admin'@'%' IDENTIFIED BY 'Blog-App-Admin-3141';

GRANT ALL PRIVILEGES ON * . * TO 'blog_app_admin'@'%';