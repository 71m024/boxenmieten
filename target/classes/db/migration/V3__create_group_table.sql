create table `GROUP` (
    ID int not null PRIMARY KEY AUTO_INCREMENT,
    NAME varchar(100) not null
);

create table USER_GROUP (
    USER_ID int not null,
    GROUP_ID int not null,
    PRIMARY KEY (USER_ID, GROUP_ID),
    FOREIGN KEY (USER_ID) REFERENCES USER(ID),
    FOREIGN KEY (GROUP_ID) REFERENCES `GROUP`(ID)
);
