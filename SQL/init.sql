CREATE TABLE users
(
    userid int AUTO_INCREMENT,
    username varchar(255) unique,
    email varchar(255),
    password byte(32),
    PRIMARY KEY (userid)
);

CREATE TABLE posts
(
    postid int AUTO_INCREMENT,
    userid int,
    content TEXT,
    PRIMARY KEY (postid),
    FOREIGN KEY (userid) REFERENCES users(userid)
);

CREATE TABLE thread
(
    postid int,
    title varchar(255),
    PRIMARY KEY (postid),
    FOREIGN KEY (postid) REFERENCES posts(postid)
)

CREATE TABLE replies
(
    replyid int,
    postid int,
    PRIMARY KEY (replyid),
    FOREIGN KEY (replyid) REFERENCES posts(postid),
    FOREIGN KEY (postid) REFERENCES thread(postid)
);