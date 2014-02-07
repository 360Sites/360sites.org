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

INSERT INTO `user` (`user_name`,`password`,`first_name`,`user_type_id`,`is_valid_user`) VALUES
('admin',MD5('admin'),'Admin',50,1),
('aram',MD5('aram'),'Aram',50,1),
('ashot',MD5('ashot'),'Ashot',50,1),
('grish',MD5('grish'),'Grish',50,1),
('karen',MD5('karen'),'Karen',50,1);


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

CREATE TABLE `notification`(  
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` INT(11) NOT NULL,
  `type` CHAR(1) NOT NULL DEFAULT 'I',
  `text_en` VARCHAR(100) NOT NULL,
  `title_en` VARCHAR(1500) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`code`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `notification` (`id`, `code`, `type`, `text_en`, `title_en`) VALUES 
('1','1000','E','user is invalid',''),
('2','1001','E','incorrect password',''),
('3','1002','E','userName \'%s\' does not exist','');
