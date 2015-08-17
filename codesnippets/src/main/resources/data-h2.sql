-- password = 'a' (encrypted)
INSERT INTO users(user_name, user_email, user_password) VALUES ('mirna', 'mdjc@gmail.com', '$2a$10$BcK5Gta9418SK5I2bLUhOeFtFXqE742ni7/xedJ8f2oZoYzWWQgfi'); 
INSERT INTO users(user_name, user_email, user_password) VALUES ('testUser', 'test123@gmail.com', '$2a$10$BcK5Gta9418SK5I2bLUhOeFtFXqE742ni7/xedJ8f2oZoYzWWQgfi');
INSERT INTO users(user_name, user_email, user_password) VALUES ('john', 'john23@gmail.com', '$2a$10$BcK5Gta9418SK5I2bLUhOeFtFXqE742ni7/xedJ8f2oZoYzWWQgfi');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code, snippet_language, snippet_description, snippet_category) 
values ((select user_id from users where user_name='testUser'), 'Binary Search', 'public class search() {}', 'Java', 'class for searching', 'search');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code, snippet_language, snippet_description, snippet_category) 
values ((select user_id from users where user_name='mirna'), 'Quit Sort', 'public class MergeSort() {}', 'Java', 'description test', 'sorting');

INSERT INTO snippet_categories (category_description) values('algorithm');
INSERT INTO snippet_categories (category_description) values('math');
INSERT INTO snippet_categories (category_description) values('combinatorics');
INSERT INTO snippet_categories (category_description) values('override method');
INSERT INTO snippet_categories (category_description) values('collections');
INSERT INTO snippet_categories (category_description) values('loops');
INSERT INTO snippet_categories (category_description) values('functions');
INSERT INTO snippet_categories (category_description) values('sorting');
INSERT INTO snippet_categories (category_description) values('searching');
INSERT INTO snippet_categories (category_description) values('strings');
INSERT INTO snippet_categories (category_description) values('files');
INSERT INTO snippet_categories (category_description) values('linux');
INSERT INTO snippet_categories (category_description) values('mac');
INSERT INTO snippet_categories (category_description) values('database');
INSERT INTO snippet_categories (category_description) values('threads');
INSERT INTO snippet_categories (category_description) values('streams');
INSERT INTO snippet_categories (category_description) values('windows');
INSERT INTO snippet_categories (category_description) values('command');