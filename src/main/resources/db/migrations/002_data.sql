-- liquibase formatted sql
-- changeset oksana.bibikova:002

insert into categories (category_id, name, image_url)
values
    (1, 'Planting material', 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/category_img/1.jpeg'),
    (2, 'Protective products and septic tanks', 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/category_img/2.jpeg'),
    (3, 'Fertilizer', 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/category_img/3.jpeg'),
    (4, 'Pots and planters', 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/category_img/4.jpeg'),
    (5, 'Tools and equipment', 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/category_img/5.jpeg');

insert into products (product_id, name, description, price, category_id, image_url, discount_price, created_at, updated_at)
values
    (1, 'Tulip', 'Elevate your garden with our exquisite Tulip planting material. Embrace the beauty of nature as these premium Tulip bulbs promise vibrant blooms in an array of enchanting colors. Cultivate your own floral haven effortlessly with our high-quality tulip bulbs. Transform your outdoor space into a mesmerizing tapestry of blossoms with ease. Shop now and let the elegance of Tulips grace your garden.', 2.0, 1, 'https://www.almanac.com/sites/default/files/styles/or/public/image_nodes/tulips-multicolored_0.jpg?itok=5KFk7THG', null, '2025-02-14 14:10:00', null),
    (2, 'Chamomile', 'Elevate your garden with our premium Chamomile planting material. Cultivate a serene oasis with these fragrant blooms. Shop now for a tranquil touch to your green space.', 1.7, 1, 'https://files.nccih.nih.gov/chamomile-steven-foster-square.jpg', NULL, '2025-02-15 11:55:00', NULL),
    (3, 'Marigold', 'Ignite your garden with vibrant Marigold blooms. Our superior planting material ensures a burst of color and vitality. Elevate your garden aesthetic—shop Marigold today.', 3.0, 1, 'https://www.gardendesign.com/pictures/images/900x705Max/dream-team-s-portland-garden_6/marigold-flowers-orange-pixabay_12708.jpg', NULL, '2025-02-18 14:15:00', NULL),
    (4, 'Diatomaceous Earth', 'Safeguard your garden with Diatomaceous Earth. A natural defense against pests, it protects plants while promoting soil health. Choose eco-friendly protection for your garden.', 10.0, 2, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/29.jpeg', NULL, '2025-02-20 12:45:00', NULL),
    (5, 'Happy Frog', 'Nurture your garden with Happy Frog—your plant''s best friend. This protective product enhances soil structure and fertility, ensuring a thriving, joyful garden. Discover the Happy Frog difference.', 12.0, 2, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/26.jpeg', NULL, '2025-02-20 13:31:00', NULL),
    (6, 'Horticultural Charcoal', 'Enhance your gardening experience with Horticultural Charcoal. This natural product purifies soil, prevents root rot, and promotes plant health. Elevate your garden''s resilience.', 13.0, 2, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/28.jpeg', 11.05, '2025-03-05 11:10:00', NULL),
    (7, 'Organic Perlite', 'Fuel your plants'' growth with Organic Perlite. This lightweight, porous medium enhances aeration and drainage, ensuring optimal conditions for your garden. Choose organic vitality.', 6.0, 3, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/24.jpeg', NULL, '2025-03-06 13:40:00', NULL),
    (8, 'Ocean Forest', 'Dive into lush, thriving gardens with Ocean Forest fertilizer. Enriched with sea ingredients, it fosters robust plant growth. Elevate your gardening experience with Ocean Forest.', 7.0, 3, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/27.jpeg', NULL, '2025-03-07 16:25:00', NULL),
    (9, 'Potting Mix', 'Nourish your plants with our Potting Mix fertilizer. Perfectly blended for optimal growth, it provides the essential nutrients your plants crave. Cultivate a flourishing garden today.', 8.5, 3, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/23.jpeg', NULL, '2025-03-08 14:15:00', NULL),
    (10, 'Greek Pot', 'Add a touch of ancient elegance with our Greek Pot. Crafted for both style and durability, this planter elevates your plants, turning your garden into a timeless sanctuary.', 30.0, 4, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/1.jpeg', 27.0, '2025-03-09 11:50:00', NULL),
    (11, 'Wicker Pot', 'Embrace natural aesthetics with our Wicker Pot. Stylish and functional, it complements any garden space. Elevate your plant display with this charming wicker planter.', 20.0, 4, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/12.jpeg', NULL, '2025-03-10 13:17:00', NULL),
    (12, 'Red Pot', 'Make a bold statement in your garden with our vibrant Red Pot. Durable and eye-catching, it adds a pop of color to your green haven. Shop now for a standout planter.', 25.0, 4, 'https://raw.githubusercontent.com/tel-ran-de/telran_backend_garden_shop/master/public/product_img/10.jpeg', NULL, '2025-03-10 13:25:00', NULL),
    (13, 'Shovel', 'Dig into gardening with our sturdy Shovel. Designed for durability and comfort, it''s an essential tool for planting and landscaping. Elevate your gardening experience with quality tools.', 40.0, 5, 'https://assets.leevalley.com/Size4/10115/PG107-radius-ergonomic-stainless-steel-shovel-u-0195.jpg', 32.0, '2025-03-11 10:25:00', NULL),
    (14, 'Rake', 'Maintain a pristine garden with our reliable Rake. Perfect for leaf and debris removal, it ensures a tidy outdoor space. Upgrade your gardening arsenal with this essential tool.', 38.0, 5, 'https://images.ctfassets.net/zma7thmmcinb/46JNtlvxFdhCD2XPHHziLc/31fe4425eff26086a7eb884a4384d85b/find-the-right-rake-plastic-rake.jpg', NULL, '2025-03-11 10:36:00', NULL),
    (15, 'Gardening scissors', 'Precision meets functionality with our Gardening Scissors. Trim and shape your garden with ease. Elevate your gardening skills with these sharp and durable scissors.', 20.0, 5, 'https://cdn.thewirecutter.com/wp-content/uploads/2015/06/pruningshears-2x1-.jpg?auto=webp&quality=75&crop=2:1&width=1024&dpr=2', NULL, '2025-03-12 12:16:00', NULL);

INSERT INTO users (user_id, name, email, phone_number, password_hash, role)
VALUES
    (1, 'John Smith', 'john.smith@example.com', '1111111111', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (2, 'Emma Johnson', 'emma.johnson@example.com', '2222222222', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (3, 'Olivia Brown', 'olivia.brown@example.com', '3333333333', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (4, 'Liam Williams', 'liam.williams@example.com', '4444444444', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (5, 'Noah Jones', 'noah.jones@example.com', '5555555555', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (6, 'Sophia Garcia', 'sophia.garcia@example.com', '6666666666', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (7, 'Mason Martinez', 'mason.martinez@example.com', '7777777777', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (8, 'Isabella Miller', 'isabella.miller@example.com', '8888888888', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (9, 'Lucas Davis', 'lucas.davis@example.com', '9999999999', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (10, 'Charlotte Rodriguez', 'charlotte.rodriguez@example.com', '1010101010', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (11, 'Elijah Hernandez', 'elijah.hernandez@example.com', '1112131415', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (12, 'Amelia Wilson', 'amelia.wilson@example.com', '1213141516', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (13, 'Benjamin Anderson', 'benjamin.anderson@example.com', '1314151617', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'CLIENT'),
    (14, 'James Thomas', 'james.thomas@example.com', '1415161718', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'ADMINISTRATOR'),
    (15, 'William White', 'william.white@example.com', '1516171819', '$2a$10$QqLIc6MHjJ4eFyvBWHY5nOY6BCrjmzq/Xs5fqqi5COJmLUE27sojS', 'ADMINISTRATOR');

insert into carts (cart_id, user_id)
values
    (1, 1),
    (2, 2),
    (3, 5);

insert into cart_items (cart_item_id, cart_id, product_id, quantity)
values
    (1, 1, 1, 3),
    (2, 2, 2, 2),
    (3, 3, 7, 7);

insert into favorites (favorite_id, user_id, product_id)
values
    (1, 1, 10),
    (2, 2, 4),
    (3, 2, 15),
    (4, 3, 6),
    (5, 3, 13),
    (6, 3, 10),
    (7, 4, 9),
    (8, 4, 1),
    (9, 5, 11),
    (10, 6, 2),
    (11, 6, 13),
    (12, 7, 11),
    (13, 7, 15);

insert into orders (order_id, delivery_address, contact_phone, delivery_method, status, created_at, updated_at, user_id)
values
    (1, '123 Main St, City', '+49123456789', 'SELF_PICKUP', 'PENDING_PAYMENT', '2025-03-13 14:15:00', null, 1),
    (2, '456 Elm St, City', '+49123456790', 'COURIER_DELIVERY', 'CREATED', '2025-03-14 10:30:00', NULL, 2),
    (3, '789 Oak St, City', '+49123456791', 'SELF_PICKUP', 'PAID', '2025-03-15 08:45:00', NULL, 3),
    (4, '321 Pine St, City', '+49123456792', 'COURIER_DELIVERY', 'IN_TRANSIT', '2025-03-16 12:10:00', NULL, 4),
    (5, '654 Birch St, City', '+49123456793', 'SELF_PICKUP', 'DELIVERED', '2025-03-17 16:25:00', NULL, 5),
    (6, '987 Cedar St, City', '+49123456794', 'COURIER_DELIVERY', 'CANCELED', '2025-03-18 09:50:00', NULL, 6),
    (7, '159 Maple St, City', '+49123456795', 'SELF_PICKUP', 'PENDING_PAYMENT', '2025-03-19 14:05:00', NULL, 7),
    (8, '753 Spruce St, City', '+49123456796', 'COURIER_DELIVERY', 'CREATED', '2025-03-20 18:40:00', NULL, 8),
    (9, '852 Walnut St, City', '+49123456797', 'SELF_PICKUP', 'PAID', '2025-03-21 11:15:00', NULL, 9),
    (10, '951 Ash St, City', '+49123456798', 'COURIER_DELIVERY', 'IN_TRANSIT', '2025-03-22 15:30:00', NULL, 10),
    (11, '753 Fir St, City', '+49123456799', 'SELF_PICKUP', 'DELIVERED', '2025-03-23 10:00:00', NULL, 11),
    (12, '357 Cherry St, City', '+49123456800', 'COURIER_DELIVERY', 'CANCELED', '2025-03-24 12:55:00', NULL, 12),
    (13, '123 Main St, City', '+49123456789', 'SELF_PICKUP', 'PENDING_PAYMENT', '2025-03-25 14:45:00', NULL, 1),
    (14, '369 Redwood St, City', '+49123456802', 'COURIER_DELIVERY', 'PAID', '2025-03-26 16:35:00', NULL, 14),
    (15, '147 Beech St, City', '+49123456803', 'SELF_PICKUP', 'IN_TRANSIT', '2025-03-27 09:20:00', NULL, 15);

insert into order_items (order_item_id, order_id, product_id, quantity, price_at_purchase)
values
    (1, 1, 1, 2, 2.00),
    (2, 1, 2, 3, 1.7),
    (3, 2, 5, 3, 12.00),
    (4, 3, 7, 2, 6.00),
    (5, 4, 11, 1, 20.00),
    (6, 5, 2, 4, 1.7),
    (7, 6, 3, 1, 3.00),
    (8, 7, 6, 5, 11.05),
    (9, 8, 15, 3, 20.00),
    (10, 9, 12, 2, 25.00),
    (11, 10, 3, 4, 3.00),
    (12, 11, 4, 1, 10.00),
    (13, 12, 1, 2, 2.00),
    (14, 13, 8, 3, 7.00),
    (15, 14, 5, 2, 12.00),
    (16, 15, 7, 4, 6.00);