CREATE TABLE patients (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    document VARCHAR(6) NOT NULL UNIQUE,

    street varchar(100) not null,
    city varchar(100) not null,
    state varchar(100) not null,
    zipcode varchar(10) not null,
    active tinyint NOT NULL DEFAULT 1,

    PRIMARY KEY (id)
);