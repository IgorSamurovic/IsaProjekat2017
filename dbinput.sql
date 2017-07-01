-- MySQL Script generated by MySQL Workbench
-- 07/01/17 04:50:41
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema isa
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `isa` ;

-- -----------------------------------------------------
-- Schema isa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `isa` DEFAULT CHARACTER SET utf8 ;
USE `isa` ;

-- -----------------------------------------------------
-- Table `isa`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`User` ;

CREATE TABLE IF NOT EXISTS `isa`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT 0,
  `activationString` VARCHAR(120) NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Restaurant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Restaurant` ;

CREATE TABLE IF NOT EXISTS `isa`.`Restaurant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Article`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Article` ;

CREATE TABLE IF NOT EXISTS `isa`.`Article` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `stock` INT NOT NULL DEFAULT 0,
  `price` FLOAT NOT NULL DEFAULT 0,
  `restaurant` INT NOT NULL,
  `type` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `FK_Article_restaurant_idx` (`restaurant` ASC),
  CONSTRAINT `FK_Article_restaurant`
    FOREIGN KEY (`restaurant`)
    REFERENCES `isa`.`Restaurant` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`FriendRequest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`FriendRequest` ;

CREATE TABLE IF NOT EXISTS `isa`.`FriendRequest` (
  `requester` INT NOT NULL,
  `requested` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `requested_idx` (`requested` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `FK_Friend_Request_requester`
    FOREIGN KEY (`requester`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Friend_Request_requested`
    FOREIGN KEY (`requested`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Reservation` ;

CREATE TABLE IF NOT EXISTS `isa`.`Reservation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `restaurant` INT NOT NULL,
  `timeFrom` TIMESTAMP NULL,
  `timeTo` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  INDEX `FKrestaurant_idx` (`restaurant` ASC),
  INDEX `FKuser_idx` (`user` ASC),
  CONSTRAINT `FK_Reservation_user`
    FOREIGN KEY (`user`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Reservation_restaurant`
    FOREIGN KEY (`restaurant`)
    REFERENCES `isa`.`Restaurant` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Ordr`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Ordr` ;

CREATE TABLE IF NOT EXISTS `isa`.`Ordr` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reservation` INT NOT NULL,
  `user` INT NOT NULL,
  `ready` TINYINT(1) NOT NULL DEFAULT 0,
  `cancelled` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_Order_user_idx` (`user` ASC),
  INDEX `FK_Order_reservation_idx` (`reservation` ASC),
  CONSTRAINT `FK_Order_user`
    FOREIGN KEY (`user`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Order_reservation`
    FOREIGN KEY (`reservation`)
    REFERENCES `isa`.`Reservation` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Invitation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Invitation` ;

CREATE TABLE IF NOT EXISTS `isa`.`Invitation` (
  `reservation` INT NOT NULL,
  `user` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `accepted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `user_idx` (`user` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `FK_Invitation_reservation`
    FOREIGN KEY (`reservation`)
    REFERENCES `isa`.`Reservation` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Invitation_user`
    FOREIGN KEY (`user`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`ArticleOrder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`ArticleOrder` ;

CREATE TABLE IF NOT EXISTS `isa`.`ArticleOrder` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `article` INT NOT NULL,
  `ordr` INT NOT NULL,
  `amount` INT NOT NULL,
  INDEX `order_idx` (`ordr` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `FK_Article_Order_article`
    FOREIGN KEY (`article`)
    REFERENCES `isa`.`Article` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Article_Order_order`
    FOREIGN KEY (`ordr`)
    REFERENCES `isa`.`Ordr` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Tabla`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Tabla` ;

CREATE TABLE IF NOT EXISTS `isa`.`Tabla` (
  `id` INT NOT NULL,
  `restaurant` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK _Table_restaurant_idx` (`restaurant` ASC),
  CONSTRAINT `FK _Table_restaurant`
    FOREIGN KEY (`restaurant`)
    REFERENCES `isa`.`Restaurant` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`ReservationTable`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`ReservationTable` ;

CREATE TABLE IF NOT EXISTS `isa`.`ReservationTable` (
  `reservation` INT NOT NULL,
  `tabla` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `FKtable_idx` (`tabla` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `FK_Reservation_Table_reservation`
    FOREIGN KEY (`reservation`)
    REFERENCES `isa`.`Reservation` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Reservation_Table_table`
    FOREIGN KEY (`tabla`)
    REFERENCES `isa`.`Tabla` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Friend`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Friend` ;

CREATE TABLE IF NOT EXISTS `isa`.`Friend` (
  `user1` INT NOT NULL,
  `user2` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `FKFriend_user2_idx` (`user2` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `FKFriend_user1`
    FOREIGN KEY (`user1`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FKFriend_user2`
    FOREIGN KEY (`user2`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `isa`.`Visit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `isa`.`Visit` ;

CREATE TABLE IF NOT EXISTS `isa`.`Visit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `reservation` INT NOT NULL,
  `coming` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_Visit_user_idx` (`user` ASC),
  INDEX `FK_Visit_reservation_idx` (`reservation` ASC),
  CONSTRAINT `FK_Visit_user`
    FOREIGN KEY (`user`)
    REFERENCES `isa`.`User` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Visit_reservation`
    FOREIGN KEY (`reservation`)
    REFERENCES `isa`.`Reservation` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `isa` ;

-- -----------------------------------------------------
-- Placeholder table for view `isa`.`view1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `isa`.`view1` (`id` INT);

-- -----------------------------------------------------
-- View `isa`.`view1`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `isa`.`view1` ;
DROP TABLE IF EXISTS `isa`.`view1`;
USE `isa`;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `isa`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `isa`;
INSERT INTO `isa`.`User` (`id`, `username`, `firstName`, `lastName`, `email`, `active`, `activationString`, `password`) VALUES (1, 'haha', 'ha', 'ha', 'randommail@GGEWEF', 1, '\"\"', 'haha');
INSERT INTO `isa`.`User` (`id`, `username`, `firstName`, `lastName`, `email`, `active`, `activationString`, `password`) VALUES (2, 'igor', 'Igor', 'Samurovic', 'igorsamurovic@hotmail.com', 1, '\"\"', 'igor');

COMMIT;


-- -----------------------------------------------------
-- Data for table `isa`.`Restaurant`
-- -----------------------------------------------------
START TRANSACTION;
USE `isa`;
INSERT INTO `isa`.`Restaurant` (`id`, `name`, `type`) VALUES (1, 'Soya', 'Chinese');
INSERT INTO `isa`.`Restaurant` (`id`, `name`, `type`) VALUES (2, 'The Old Spaghetti Factory', 'Italian');
INSERT INTO `isa`.`Restaurant` (`id`, `name`, `type`) VALUES (3, 'Itadakimasu!', 'Japanese');

COMMIT;


-- -----------------------------------------------------
-- Data for table `isa`.`Article`
-- -----------------------------------------------------
START TRANSACTION;
USE `isa`;
INSERT INTO `isa`.`Article` (`id`, `name`, `stock`, `price`, `restaurant`, `type`) VALUES (1, 'Italian Cheese', 2, 15, 2, 0);
INSERT INTO `isa`.`Article` (`id`, `name`, `stock`, `price`, `restaurant`, `type`) VALUES (2, 'Italian Wine', 15, 20, 2, 1);
INSERT INTO `isa`.`Article` (`id`, `name`, `stock`, `price`, `restaurant`, `type`) VALUES (3, 'Chinese Rice', 20, 12, 1, 0);
INSERT INTO `isa`.`Article` (`id`, `name`, `stock`, `price`, `restaurant`, `type`) VALUES (4, 'Chinese Mijiu', 25, 10, 1, 1);
INSERT INTO `isa`.`Article` (`id`, `name`, `stock`, `price`, `restaurant`, `type`) VALUES (5, 'Sake', 5, 15, 3, 1);
INSERT INTO `isa`.`Article` (`id`, `name`, `stock`, `price`, `restaurant`, `type`) VALUES (6, 'Mochi', 12, 20, 3, 0);
INSERT INTO `isa`.`Article` (`id`, `name`, `stock`, `price`, `restaurant`, `type`) VALUES (7, 'Anpan', 13, 25, 3, 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `isa`.`FriendRequest`
-- -----------------------------------------------------
START TRANSACTION;
USE `isa`;
INSERT INTO `isa`.`FriendRequest` (`requester`, `requested`, `id`) VALUES (2, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `isa`.`Tabla`
-- -----------------------------------------------------
START TRANSACTION;
USE `isa`;
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (1, 2);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (2, 2);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (3, 2);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (4, 2);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (5, 1);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (6, 1);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (7, 1);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (8, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (9, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (10, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (11, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (12, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (13, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (14, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (15, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (16, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (17, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (18, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (19, 3);
INSERT INTO `isa`.`Tabla` (`id`, `restaurant`) VALUES (20, 3);

COMMIT;

