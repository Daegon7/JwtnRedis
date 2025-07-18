CREATE TABLE users (
    id VARCHAR(8) PRIMARY KEY,
    password VARCHAR(8),
    name VARCHAR(20),
    role VARCHAR(5)
);

INSERT INTO users (id, password, name, role) VALUES
('user01', 'pass1234', 'Alice Kim', 'admin'),
('user02', 'pass5678', 'Bob Lee', 'user'),
('user03', 'pass9012', 'Charlie Park', 'guest'),
('user04', 'pass3456', 'Dana Choi', 'user'),
('user05', 'pass7890', 'Evan Jung', 'admin');


