CREATE TABLE Users (
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(1248) NOT NULL,
  email VARCHAR(50) NOT NULL,
  PRIMARY KEY(username)
);
INSERT INTO Users (firstName, lastName, username, password, email) VALUES ('syed', 'abdali', 'syed001', 'syedPass','syed@hotmail.com');


CREATE TABLE Address (
    street VARCHAR(100) NOT NULL,
    province VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL,
    PRIMARY KEY(username)
);

CREATE TABLE Book (
  bid VARCHAR(20) NOT NULL,
  title VARCHAR(60) NOT NULL,
  price INT NOT NULL,
  category VARCHAR(60) NOT NULL,
  PRIMARY KEY(bid)
);

INSERT INTO Book (bid, title, price, category) VALUES ('b001', 'Little Prince', 20, 'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b002','Physics', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b003','Mechanics' ,100,'Engineering');

CREATE TABLE Review(
	username VARCHAR(50) NOT NULL,
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(50) NOT NULL,
	text VARCHAR(1000) NOT NULL,
	PRIMARY KEY(username, bid),
	FOREIGN KEY(username) REFERENCES Users(username) ON DELETE CASCADE,
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);
insert into Review (username, bid, title, text) VALUES ('syed001','b001','This book is Ass','I cant read tbh.');



CREATE TABLE PO (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    lname VARCHAR(20) NOT NULL,
    fname VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    address INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (address) REFERENCES Address (id) ON DELETE CASCADE
);

CREATE INDEX address ON PO(address)
CREATE TABLE POItem (
    id INT NOT NULL,
    bid VARCHAR(20) NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY(id,bid),
    FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
    FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);

CREATE INDEX id ON POItem(id);
CREATE INDEX bid ON POItem(bid);