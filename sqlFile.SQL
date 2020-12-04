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
INSERT INTO Book (bid, title, price, category) VALUES ('b002','Mechanics', 201, 'Engineering');
INSERT INTO Book (bid, title, price, category) VALUES ('b003','Physics', 201, 'Science');
INSERT INTO Book (bid, title, price, category) VALUES ('b004','Harry Potter and the Cursed Child' ,100,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b005','Howls Moving Castle' ,19,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b006','The Hobbit' ,18,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b007','Neverwhere' ,11,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b008','The Witcher' ,11,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b009','The Golden Compass' ,15,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b010','Harry Potter and the Philosophers Stone' ,14,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b011','Harry Potter and the Chamber of Secrets' ,13,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b012','Harry Potter and the Goblet of Fire' ,15,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b013','Quidditch Through the Ages' ,10,'Fantasy');
INSERT INTO Book (bid, title, price, category) VALUES ('b014','2001 A Space Odyssey' ,20,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b015','Do Androids Dream of Electric Sheep' ,10,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b016','Jurassic Park' ,10,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b017','The Lost World' ,10,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b018','Star Wars Heir to the Empire' ,10,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b019','Star Wars Light of the Jedi' ,15,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b020','Star Wars Thrawn' ,15,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b021','Ready Player Two' ,10,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b022','Ready Player One' ,10,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b023','Hitchhikers Guide to the Galaxy' ,10,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b024','Star Trek Picard' ,20,'Science Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b025','The Guest List' ,20,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b026','A Time For Mercy' ,20,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b027','The Vanishing Half' ,30,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b028','The Flight Attendant' ,25,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b029','The Return' ,10,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b030','Unsolved' ,20,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b031','Anxious People' ,30,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b032','Invisible Girl' ,10,'Fiction');
INSERT INTO Book (bid, title, price, category) VALUES ('b033','The Sentinel' ,15,'Fiction');

CREATE TABLE Review(
	username VARCHAR(50) NOT NULL,
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(50) NOT NULL,
	text VARCHAR(1000) NOT NULL,
	rating INT NOT NULL,
	PRIMARY KEY(username, bid),
	FOREIGN KEY(username) REFERENCES Users(username) ON DELETE CASCADE,
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);
insert into Review (username, bid, title, text, rating) VALUES ('syed001','b001','This book is Ass','I cant read tbh.');


CREATE TABLE ORDERS(
    id VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    province VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    billingstreet VARCHAR(100) NOT NULL,
    billingprovince VARCHAR(20) NOT NULL,
    billingcountry VARCHAR(20) NOT NULL,
    billingzip VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE ORDERITEMS(
    id VARCHAR(100) NOT NULL,
    bid VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(id, bid),
    FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE,
    FOREIGN KEY(id) REFERENCES ORDERS(id) ON DELETE CASCADE

);