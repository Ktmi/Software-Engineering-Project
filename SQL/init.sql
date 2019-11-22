CREATE TABLE users
(
    userid INT AUTO_INCREMENT NOT NULL,
    username varchar(255) UNIQUE NOT NULL,
    email varchar(255) NOT NULL,
    secret byte(32) NOT NULL,
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

CREATE TABLE threads
(
    postid int,
    title varchar(255),
    PRIMARY KEY (postid),
    FOREIGN KEY (postid) REFERENCES post(postid)
);

CREATE TABLE replies
(
    postid int,
    threadid int,
    PRIMARY KEY (postid),
    FOREIGN KEY (postid) REFERENCES post(postid),
    FOREIGN KEY (threadid) REFERENCES thread(postid)
);