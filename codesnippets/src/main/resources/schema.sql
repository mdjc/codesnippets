CREATE TABLE USERS (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(35) NOT NULL UNIQUE,
	user_email VARCHAR(60) NOT NULL,
	user_password VARCHAR(60) NOT NULL
);

CREATE TABLE snippets (
	snippet_id INT AUTO_INCREMENT PRIMARY KEY,
	snippet_user INT NOT NULL REFERENCES users(user_id),
	snippet_title VARCHAR(60) NOT NULL,
	snippet_code VARCHAR(4000) NOT NULL,
	UNIQUE(snippet_user, snippet_title)
);


