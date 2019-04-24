DROP TABLE phone;
DROP TABLE user_role;
DROP TABLE role;
DROP TABLE user;

CREATE TABLE user (
    id             INTEGER AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(100) NOT NULL,
    last_name      VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL
);

CREATE TABLE role (
    id     INTEGER AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(50) NOT NULL
);

CREATE TABLE user_role (
    id          INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id     INTEGER NOT NULL,
    role_id     INTEGER NOT NULL,

    CONSTRAINT ur_user_fk FOREIGN KEY ( user_id )
        REFERENCES user ( id ),
    CONSTRAINT ur_role_fk FOREIGN KEY ( role_id )
        REFERENCES role ( id )
);

CREATE TABLE phone (
    id               INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id          INTEGER NOT NULL,
    phone_number     VARCHAR(13) NOT NULL,

    CONSTRAINT phone_user_fk FOREIGN KEY ( user_id )
        REFERENCES user ( id )
);