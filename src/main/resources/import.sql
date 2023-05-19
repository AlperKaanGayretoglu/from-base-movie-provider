INSERT INTO users (id, created, updated, email, name, password_hash, recovery_code, recovery_code_expiration_date,
                   surname, role, verification_code, verification_code_expiration_date, is_verified)
VALUES ('user1', now(), now(), 'test@test.com', 'John', '$2a$10$aqvJCH43/2Nt1JmoT5nn5OrnFt05VtjE87lI/xw7mnoSKO.NejYfW',
        null, null, 'Doe', 'ADMIN', null, null, true);

-- User Data (admin pass is 123456789)
INSERT INTO users (id, email, name, surname, password_hash, role, is_verified, created, updated)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', 'alperkaangayretoglu@gmail.com', 'Alper', 'Gayretoğlu',
        '$2a$10$OU9/JC80FudcjfuAj1.X5OopuhG6Trs3JHQEOHdH.Xnm7VSIpW0OC', 'ADMIN', true, now(), now());

INSERT INTO users (id, email, name, surname, password_hash, role, is_verified, created, updated)
VALUES ('6b8a3d25-2b7a-4683-89ed-ac0e42cdc878', 'guest@mail.com', 'Guest', 'Guest',
        '$2a$10$OU9/JC80FudcjfuAj1.X5OopuhG6Trs3JHQEOHdH.Xnm7VSIpW0OC', 'GUEST', true, now(), now());

-- Subscription Data
INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created, updated)
VALUES ('11b455da-715a-4dc1-b4f1-b526c1c9ab4e', 1, true, 100, 'GOLD', now(), now());

INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created, updated)
VALUES ('33b455da-335a-4dc1-b4f1-b526c1c9ab4e', 3, true, 90, 'PLATINUM', now(), now());

INSERT INTO subscription (id, duration, is_active, monthly_fee, name, created, updated)
VALUES ('66b455da-665a-4dc1-b4f1-b526c1c9ab4e', 6, true, 80, 'DIAMOND', now(), now());


-- Category Data
INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', 'SUPER CATEGORY', null, true, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('66403305-972b-42b1-a71a-d7bb2828eebe', 'HORROR', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', false, now(), now());

INSERT INTO category (id, name, parent_id, is_super_category, created, updated)
VALUES ('50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4', 'ACTION', '10b455da-7e5a-4dc3-b4f5-b526c1c9ab4e', false, now(), now());


-- Movie Data
INSERT INTO movie (id, title, created, updated)
VALUES ('b4dceb23-d2ea-4432-aa7a-c71b4b15bcee', 'The Walking Dead', now(), now());

INSERT INTO movie (id, title, created, updated)
VALUES ('cbf27a05-9abe-40c0-a943-ede62f9ca3de', 'John Wick', now(), now());

INSERT INTO movie (id, title, created, updated)
VALUES ('421450be-91e2-4184-8450-3dcc12a33e63', 'Matrix', now(), now());


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
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879',
        'b4dceb23-d2ea-4432-aa7a-c71b4b15bcee'); -- admin favorite The Walking Dead

INSERT INTO users_movies (user_id, movie_id)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', 'cbf27a05-9abe-40c0-a943-ede62f9ca3de');
-- admin favorites nfs mw


-- User Interest : following categories : users_categories join table
INSERT INTO users_categories (user_id, categories_id)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', '66403305-972b-42b1-a71a-d7bb2828eebe'); -- admin follows HORROR

INSERT INTO users_categories (user_id, categories_id)
VALUES ('5b8a3d25-2b7a-4683-89ed-ac0e42cdc879', '50a5fc87-4cbe-4b50-ac5a-acdd90bbfbf4'); -- admin follows ACTION
