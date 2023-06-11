-- User Data (admin pass is 123456789)
INSERT INTO users (id, created, updated, email, password_hash, name, surname, role,
                   recovery_code, recovery_code_expiration_date,
                   verification_code, verification_code_expiration_date, is_verified)
VALUES ('admin', now(), now(), 'admin@mail.com', '$2a$10$OU9/JC80FudcjfuAj1.X5OopuhG6Trs3JHQEOHdH.Xnm7VSIpW0OC',
        'ADMIN', 'ADMIN', 'ADMIN', null, null, null, null, true);

INSERT INTO users (id, created, updated, email, password_hash, name, surname, role, is_verified)
VALUES ('guest', now(), now(), 'guest@mail.com', '$2a$10$OU9/JC80FudcjfuAj1.X5OopuhG6Trs3JHQEOHdH.Xnm7VSIpW0OC',
        'GUEST', 'GUEST', 'GUEST', true);

-- Subscription Data
INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created, updated)
VALUES ('11b455da-715a-4dc1-b4f1-b526c1c9ab4e', 1, true, 100, 'GOLD', now(), now());

INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created, updated)
VALUES ('33b455da-335a-4dc1-b4f1-b526c1c9ab4e', 3, true, 90, 'PLATINUM', now(), now());

INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created, updated)
VALUES ('66b455da-665a-4dc1-b4f1-b526c1c9ab4e', 6, true, 80, 'DIAMOND', now(), now());


-- Category Data
INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'SUPER CATEGORY', null,
        true, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('66403305-972b-42b1-a71a-d7bb2828eebe', 'HORROR', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e',
        false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4', 'ACTION', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e',
        false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf5', 'COMEDY', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e',
        false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf6', 'ROMANTIC', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e',
        false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf7', 'ROMANTIC COMEDY', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e',
        false, now(), now());

-- Movie Data
INSERT INTO movie (id, created, updated, title, description, movie_length)
VALUES ('b4dceb23-d2ea-4432-aa7a-c71b4b15bcee', now(), now(), 'The Walking Dead', 'description', 60);

INSERT INTO movie (id, created, updated, title, description, movie_length)
VALUES ('cbf27a05-9abe-40c0-a943-ede62f9ca3de', now(), now(), 'John Wick', 'description', 60);

INSERT INTO movie (id, created, updated, title, description, movie_length)
VALUES ('421450be-91e2-4184-8450-3dcc12a33e63', now(), now(), 'Matrix', 'description', 60);


-- category_movies join table
INSERT INTO category_movies (categories_id, movies_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e',
        'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- super category : The Walking Dead

INSERT INTO category_movies (categories_id, movies_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'cbf27a05-9abe-40c0-a943-ede62f9ca3de'); -- super category : John Wick

INSERT INTO category_movies (categories_id, movies_id)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', '421450be-91e2-4184-8450-3dcc12a33e63'); -- super category : Matrix

INSERT INTO category_movies (categories_id, movies_id)
VALUES ('66403305-972b-42b1-a71a-d7bb2828eebe', 'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee');
-- horror : The Walking Dead


-- User Interest : favorite movies : users_movies join table
INSERT INTO users_movies (user_id, movie_id)
VALUES ('guest', 'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- guest favorites The Walking Dead

INSERT INTO users_movies (user_id, movie_id)
VALUES ('guest', 'cbf27a05-9abe-40c0-a943-ede62f9ca3de');
-- guest favorites John Wick


-- User Interest : following categories : users_categories join table
INSERT INTO users_categories (user_id, categories_id)
VALUES ('guest', '66403305-972b-42b1-a71a-d7bb2828eebe'); -- guest follows HORROR

INSERT INTO users_categories (user_id, categories_id)
VALUES ('guest', '50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4');
-- guest follows ACTION

-- File Data
INSERT INTO file_data (id, created, updated, name, content, file_type, byte_size)
VALUES ('b4dceb23-d2ea-4432-aa7a-c71b4b15bcee', now(), now(), 'hello.txt', 'HELLO', 0, 5);