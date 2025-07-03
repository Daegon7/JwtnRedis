-- 테이블 생성
CREATE TABLE USERS (
  ID VARCHAR(8) PRIMARY KEY,
  PASSWORD VARCHAR(8),
  NAME NVARCHAR(20),
  ROLE NVARCHAR(5)
);

-- 데이터 삽입 (한글 문자열은 반드시 N'문자열')
INSERT INTO USERS VALUES ('test', 'test123', N'관리자', N'Admin');
INSERT INTO USERS VALUES ('user1', 'user1', N'홍길동', N'User');

-- 데이터 조회
SELECT * FROM USERS;

