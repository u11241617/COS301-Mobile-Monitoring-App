CREATE TABLE user (
  user_id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  email varchar(255) UNIQUE NOT NULL,
  password varchar(255) NOT NULL,
  registration_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
)