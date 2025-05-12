CREATE TABLE user(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL
);

CREATE TABLE schedule(
    content_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    created_time TIMESTAMP,
    updated_time TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);