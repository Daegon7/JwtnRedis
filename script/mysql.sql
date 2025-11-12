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

CREATE TABLE appliance (
    id VARCHAR(10) PRIMARY KEY,       -- A1, A2 같은 문자열 ID
    name VARCHAR(100) NOT NULL,       -- 제품명
    brand VARCHAR(50) NOT NULL,       -- 브랜드명
    price INT NOT NULL,               -- 가격 (정수)
    description VARCHAR(255)          -- 설명
);


INSERT INTO appliance (id, name, brand, price, description)
VALUES
('A1', 'Smart Refrigerator', 'Samsung', 1200000, 'AI 냉장관리 기능 포함'),
('A2', 'Washer X200', 'LG', 890000, 'AI 스팀 살균 세탁기');


CREATE TABLE user (
    id VARCHAR(10) PRIMARY KEY,        -- "1", "2" 같은 문자열 ID
    name VARCHAR(50) NOT NULL,         -- 사용자 이름
    email VARCHAR(100) NOT NULL UNIQUE,-- 이메일 (중복 방지)
    description VARCHAR(255)           -- 설명
);


INSERT INTO user (id, name, email, description)
VALUES
('1', 'daegon', 'daegon@example.com', '첫 번째 사용자'),
('2', 'neo', 'neo@example.com', '두 번째 사용자');


