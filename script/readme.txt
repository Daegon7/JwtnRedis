도커 데스크탑 설치 먼저
- mssql 설치 (ssms editor 추가 설치)
-- docker pull mcr.microsoft.com/mssql/server:2022-latest
-- docker run -e "ACCEPT_EULA=Y"   -e "MSSQL_SA_PASSWORD=YourStrong@Passw0rd"   -p 1433:1433   --name mssql2022   -d mcr.microsoft.com/mssql/server:2022-latest
- Redis 설치
--docker pull redis
--docker run -d --name my-redis-secure -p 6379:6379 redis redis-server --requirepass "yourStrongPassword"

Intellij VM설정
--sun-misc-unsafe-memory-access=allow
