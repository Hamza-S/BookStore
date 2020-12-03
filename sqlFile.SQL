CREATE TABLE Users (
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  PRIMARY KEY(username)
);

CREATE TABLE Address (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    street VARCHAR(100) NOT NULL,
    province VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (username) REFERENCES USERS(username) ON DELETE CASCADE
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