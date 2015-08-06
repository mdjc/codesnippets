INSERT INTO snippets (snippet_user, snippet_title, snippet_code, snippet_language, snippet_description) 
values ((select user_id from users where user_name='testUser'), 'Binary Search', 'public class search() {}', 'Java', 'class for searching');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code, snippet_language, snippet_description)
values ((select user_id from users where user_name='testUser'), 'Find Object', 'public class search() {}', 'Java', 'description test');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code) 
values ((select user_id from users where user_name='mirna'), 'Merge Sort', 'public class MergeSort() {}');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code) 
values ((select user_id from users where user_name='mirna'), 'Heap Sort', 'public class MergeSort() {}');

INSERT INTO snippets (snippet_user, snippet_title, snippet_code) 
values ((select user_id from users where user_name='mirna'), 'Quit Sort', 'public class MergeSort() {}');