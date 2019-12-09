SetUp details

1. install java

Install jdk11
Verify installed java version by running command

java -version

javac -version

2. Setup Postgres using docker
    
    2a.Install Dockerhub for mac.
    
    Create an account in the dockerhub and login
    
    Start db command:
    docker run --name postgresdb -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres

    Install below tools to connect to the database

       brew cask install pgadmin4   
       brew install psqlodbc

    Connect to db command:
    psql -h localhost -U postgres -d postgres

   2b.Install Dockerhub for windows
   
    Create an account in the dockerhub and login
    
    Start db command:
    docker run --name postgresdb -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres

    Install below tools to connect to the database
    
      choco install psqlodbc
      
    Connect to db command:
      docker exec -it postgres psql -U postgres
      
 3. Insert appid,appsecret and useraccess token from facebook in fb_security table.


