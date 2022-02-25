# petstore-monolithic-java
PetStore web application implementation in Java with a monolithic architecture.

## Requirements
- PostgreSQL

Additionally a PostgreSQL Database created with the following script: https://github.com/P10-energy-consumption/PetStore/blob/master/postgres_db_script
- Apache Maven
- Docker

## Building and running
Step by step guide:
- Download: https://maven.apache.org/download.cgi
- Navigate to the PetStore repository in terminal
- Building WAR file: `mvn clean install`
- Building Docker container: `docker build -t petstore-java .`
- Running Docker container: `docker run -p 8080:8080 petstore-java`
- Navigate to: http://localhost:8080/PetStore-1.0-SNAPSHOT/api/v1/hello-world

## Database
In ConnectionFactory.java, the connection string should refer to the host IP address and not localhost which would become the Docker containers database which does not exist.

**Fix**
Change line 20 `"jdbc:postgresql://localhost:5432/postgres"` to `"jdbc:postgresql://<your-ip-address-here>:5432/postgres"`

### Connection refused
If a Connection refused exception is thrown, the PostgreSQL configuration needs to be changed. Add the following line to the PostgreSQL pg_hba.conf file
- `host  all  all 0.0.0.0/0 md5`
This is unsafe and will let everyone on the network connect to your PostgreSQL database with the correct credentials.
We are currently looking into finding a more safe way.