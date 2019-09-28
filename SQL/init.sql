CREATE TABLE users (
    username varchar(40) unique,
    email varchar(255),
    password varchar(255),
    PRIMARY KEY (username)
);

CREATE TABLE posts (
    postID int unique,
    username varchar(40) unique,
    text varchar(255),
    password varchar(255),
    replyTo int unique,
    FOREIGN KEY (username) REFERENCES users(username),
    PRIMARY KEY (postID)
);
