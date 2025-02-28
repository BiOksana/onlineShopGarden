-- MySQL Script generated by MySQL Workbench
-- Fri Feb 28 17:50:41 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gardenapp
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `gardenapp` ;

-- -----------------------------------------------------
-- Schema gardenapp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gardenapp` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `gardenapp` ;

-- -----------------------------------------------------
-- Table `gardenapp`.`cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`cart` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`cart` (
  `cart_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`cart_id`),
  INDEX `fk_cart_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `gardenapp`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`cart_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`cart_items` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`cart_items` (
  `cart_item_id` INT NOT NULL,
  `cart_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NULL,
  PRIMARY KEY (`cart_item_id`),
  INDEX `fk_cart_items_cart1_idx` (`cart_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_items_cart1`
    FOREIGN KEY (`cart_id`)
    REFERENCES `gardenapp`.`cart` (`cart_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`categories` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`categories` (
  `category_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`favorites`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`favorites` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`favorites` (
  `favorite_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`favorite_id`),
  INDEX `fk_favorites_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_favorites_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `gardenapp`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`order_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`order_items` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`order_items` (
  `order_item_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `price_at_purchase` DECIMAL(10,2) NOT NULL,
  `order_items_order_item_id` INT NOT NULL,
  PRIMARY KEY (`order_item_id`),
  INDEX `fk_order_items_orders1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_items_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `gardenapp`.`orders` (`oder_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`orders` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`orders` (
  `oder_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `delivery_address` VARCHAR(155) NOT NULL,
  `contact_phone` VARCHAR(45) NOT NULL,
  `delivery_method` VARCHAR(45) NOT NULL,
  `status` ENUM('CREATED', ' PENDING_PAYMENT', ' PAID', ' IN_TRANSIT', ' DELIVERED', 'CANCELED') NOT NULL,
  `updated_at` TIMESTAMP NULL,
  PRIMARY KEY (`oder_id`),
  INDEX `fk_orders_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `gardenapp`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`products` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `category_id` INT NOT NULL,
  `image_url` VARCHAR(45) NOT NULL,
  `discount_price` DECIMAL(10,2) NULL,
  `created_at` TIMESTAMP NULL,
  `updated_at` TIMESTAMP NULL,
  PRIMARY KEY (`product_id`),
  INDEX `fk_products_categories_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_products_categories`
    FOREIGN KEY (`category_id`)
    REFERENCES `gardenapp`.`categories` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_favorites1`
    FOREIGN KEY (`product_id`)
    REFERENCES `gardenapp`.`favorites` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_order_items1`
    FOREIGN KEY (`product_id`)
    REFERENCES `gardenapp`.`order_items` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_products_cart_items1`
    FOREIGN KEY (`product_id`)
    REFERENCES `gardenapp`.`cart_items` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`timestamps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`timestamps` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`timestamps` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL);

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gardenapp`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gardenapp`.`users` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `gardenapp`.`users` (
  `user_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `password_hash` VARCHAR(45) NOT NULL,
  `role` ENUM('CLIENT', 'ADMINISTRATOR') NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
