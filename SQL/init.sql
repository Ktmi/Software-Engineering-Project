CREATE TABLE user
(
    userid INT AUTO_INCREMENT NOT NULL,
    username varchar(255) UNIQUE NOT NULL,
    email varchar(255) NOT NULL,
    password byte(32) NOT NULL,
    PRIMARY KEY (userid)
);

CREATE TABLE post
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
    FOREIGN KEY (postid) REFERENCES post(postid)
)

CREATE TABLE reply
(
    replyid int,
    postid int,
    PRIMARY KEY (replyid),
    FOREIGN KEY (replyid) REFERENCES post(postid),
    FOREIGN KEY (postid) REFERENCES thread(postid)
);