DROP SCHEMA IF EXISTS `mediscreenapp` ;

CREATE SCHEMA `mediscreenapp` DEFAULT CHARACTER SET utf8 ;

USE `mediscreenapp` ;
CREATE TABLE IF NOT EXISTS `mediscreenapp`.`patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(125) NOT NULL,
  `last_name` VARCHAR(125) NOT NULL,
  `birth_date` DATE NOT NULL,
  `gender` VARCHAR(25) NOT NULL,
  `email_address` VARCHAR(125),
  `phone_number` VARCHAR(125),
  PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `mediscreenapp`.`addresses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(125),
  `city` VARCHAR(125),
  `zipcode` VARCHAR(125) ,
  `state` VARCHAR(125),
  `country` VARCHAR(125),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES patients(`id`)
);

CREATE TABLE IF NOT EXISTS `patients_sequence` (
    `next_val` int DEFAULT NULL
);