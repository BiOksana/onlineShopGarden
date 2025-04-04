-- liquibase formatted sql
-- changeset oksana.bibikova:001

CREATE TABLE IF NOT EXISTS categories (
  category_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  image_url VARCHAR(255) NOT NULL,
  PRIMARY KEY (category_id));

CREATE TABLE IF NOT EXISTS products (
  product_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  description TEXT NULL,
  price DECIMAL(10,2) NOT NULL,
  category_id INT NOT NULL,
  image_url VARCHAR(255) NOT NULL,
  discount_price DECIMAL(10,2) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (product_id),
   CONSTRAINT fk_products_categories
    FOREIGN KEY (category_id)
    REFERENCES categories (category_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS users (
  user_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(45) UNIQUE NOT NULL,
  phone_number VARCHAR(45) NOT NULL,
  password_hash VARCHAR(155) NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_id));

CREATE TABLE IF NOT EXISTS orders (
  order_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  delivery_address VARCHAR(155) NOT NULL,
  contact_phone VARCHAR(45) NOT NULL,
  delivery_method VARCHAR(45) NOT NULL,
  status VARCHAR(45) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (order_id),
  CONSTRAINT fk_orders_users1
    FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS order_items (
  order_item_id INT NOT NULL AUTO_INCREMENT,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  price_at_purchase DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (order_item_id),
  CONSTRAINT fk_order_items_orders1
    FOREIGN KEY (order_id)
    REFERENCES orders (order_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_order_items_products1
    FOREIGN KEY (product_id)
    REFERENCES products (product_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS carts (
  cart_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  PRIMARY KEY (cart_id),
  CONSTRAINT fk_carts_users1
    FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS cart_items (
  cart_item_id INT NOT NULL AUTO_INCREMENT,
  cart_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  PRIMARY KEY (cart_item_id),
  CONSTRAINT fk_cart_items_carts1
    FOREIGN KEY (cart_id)
    REFERENCES carts (cart_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_cart_items_products1
    FOREIGN KEY (product_id)
    REFERENCES products (product_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS favorites (
  favorite_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  product_id INT NOT NULL,
  PRIMARY KEY (favorite_id),
  CONSTRAINT fk_favorites_users1
    FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_favorites_products1
    FOREIGN KEY (product_id)
    REFERENCES products (product_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);