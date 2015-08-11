CREATE TABLE users (
	user_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(35) NOT NULL UNIQUE,
	user_email VARCHAR(60) NOT NULL,
	user_password VARCHAR(60) NOT NULL
);

CREATE TABLE snippets (
	snippet_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	snippet_user BIGINT UNSIGNED NOT NULL REFERENCES users(user_id),
	snippet_title VARCHAR(60) NOT NULL,
	snippet_code VARCHAR(4000) NOT NULL,
	snippet_description VARCHAR(330),
	snippet_language VARCHAR(40),
	snippet_category VARCHAR(30),
	UNIQUE(snippet_user, snippet_title)
);
