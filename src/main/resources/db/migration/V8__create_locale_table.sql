CREATE TABLE `LOCALE` (
    ID int not null PRIMARY KEY AUTO_INCREMENT,
    NAME varchar(100) not null,
    CITY varchar(100) not null,
    ADDRESS varchar(100) not null,
    LONGITUDE float not null,
    LATITUDE float not null,
    PICKUP_DAY varchar(100) not null,
    RETURN_DAY varchar(100) not null,
    PICKUP_TIME_START varchar(100) not null,
    PICKUP_TIME_END varchar(100) not null,
    RETURN_TIME_START varchar(100) not null,
    RETURN_TIME_END varchar(100) not null
);

CREATE TABLE `HOST` (
    USER_ID int not null,
    LOCALE_ID int not null,
    PRIMARY KEY (USER_ID, LOCALE_ID),
    FOREIGN KEY (USER_ID) REFERENCES `USER`(ID),
    FOREIGN KEY (LOCALE_ID) REFERENCES `LOCALE`(ID)
);

INSERT INTO `ROLE` (NAME)
VALUES ('EDIT_LOCALES');

INSERT INTO GROUP_ROLE (GROUP_ID, ROLE_ID) VALUES  
(
    (SELECT ID FROM `GROUP` WHERE NAME = 'CEO'),
    (SELECT ID FROM `ROLE` WHERE NAME = 'EDIT_LOCALES')
)
