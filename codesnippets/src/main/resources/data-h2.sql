INSERT INTO users(user_name, user_email, user_password) VALUES ('mirna', 'mdjc@gmail.com', 'mirna123');
INSERT INTO users(user_name, user_email, user_password) VALUES ('testUser', 'test123@gmail.com', 'test123');
INSERT INTO users(user_name, user_email, user_password) VALUES ('john', 'john23@gmail.com', '12345@5');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code) values ((select user_id from users where user_name='testUser'), 'Binary Search', 'public class search() {}');
INSERT INTO snippets (snippet_user, snippet_title, snippet_code) values ((select user_id from users where user_name='mirna'), 'Quit Sort', 'public class MergeSort() {}');