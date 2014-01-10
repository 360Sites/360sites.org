CREATE TABLE `user`(  
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` CHAR(50) NOT NULL,
  `password` CHAR(100) NOT NULL,
  `email` VARCHAR(50),
  `phon_number` VARCHAR(20),
  `first_name` VARCHAR(50),
  `last_name` VARCHAR(50),
  `user_type_id` INT(11) NOT NULL DEFAULT 1,
  `creation_date` DATETIME,
  `is_valid_email` BOOL DEFAULT 0,
  `is_valid_phon_number` BOOL DEFAULT 0,
  `is_valid_user` BOOL DEFAULT 0,
  `last_login_date` DATETIME,
  `session_id` VARCHAR(150),
  `forgot_password_token` VARCHAR(100),
  `forgot_password_token_expiry_date` DATETIME,
  PRIMARY KEY (`id`),
  INDEX (`user_name`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `user_type`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` CHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `user_type` (`id`,`name`) VALUES
(1,'guest'),(2,'user'),(50,'admin');

CREATE TABLE `object`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `name` VARCHAR(150),
  `location` VARCHAR(255),
  `description` VARCHAR(4500),
  PRIMARY KEY (`id`),
  INDEX (`user_id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;
