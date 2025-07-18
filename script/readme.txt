도커 데스크탑 설치 먼저
- mssql 설치 (ssms editor 추가 설치)
-- docker pull mysql:8.0.42
-- docker run -d   --name admin  -e MYSQL_ROOT_PASSWORD=YourStrong@Passw0rd  -p 3306:3306   mysql:8.0.42
- Redis 설치
--docker pull redis
--docker run -d --name my-redis-secure -p 6379:6379 redis redis-server --requirepass "yourStrongPassword"
- Jenkins 설치
-- docker pull jenkins/jenkins:lts
-- docker run -d   --name jenkins   -p 8083:8080    -p 50000:50000   -v jenkins_home:/var/jenkins_home   jenkins/jenkins:lts

Intellij VM설정
- --sun-misc-unsafe-memory-access=allow

1. Redis & mysql 도커에서 서버 실행
2. mysql 설치 후 테이블 생성 및 데이터 입력 (C:\workspace\gradle\JwtnRedis\script\mysql.sql)
3. http://localhost:9081/
4.