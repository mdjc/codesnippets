-- password = 'a' (encrypted)
INSERT INTO users(user_name, user_email, user_password) VALUES ('mirna', 'mdjc@gmail.com', '$2a$10$BcK5Gta9418SK5I2bLUhOeFtFXqE742ni7/xedJ8f2oZoYzWWQgfi'); 
INSERT INTO users(user_name, user_email, user_password) VALUES ('testUser', 'test123@gmail.com', '$2a$10$BcK5Gta9418SK5I2bLUhOeFtFXqE742ni7/xedJ8f2oZoYzWWQgfi');
INSERT INTO users(user_name, user_email, user_password) VALUES ('john', 'john23@gmail.com', '$2a$10$BcK5Gta9418SK5I2bLUhOeFtFXqE742ni7/xedJ8f2oZoYzWWQgfi');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code) values ((select user_id from users where user_name='testUser'), 'Binary Search', 'public class search() {}');
INSERT INTO snippets (snippet_user, snippet_title, snippet_code) values ((select user_id from users where user_name='mirna'), 'Quit Sort', 'public class MergeSort() {}');