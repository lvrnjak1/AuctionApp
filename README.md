# AuctionApp

### Set up and start instructions

##### Backend application
You can find the source code for the backend application in the **backend** directory. It is a Spring Boot project. To set up and start the server locally:

- make sure you have **git**, **Java Development Kit**, **Maven** and **PostgreSQL** installed
- create a PostgreSQL database on your machine 
- set up following environment variables on your machine:

```shell
JDBC_DATABASE_URL
JDBC_DATABASE_USERNAME
JDBC_DATABASE_PASSWORD
JWT_SECRET
SERVER_PORT
```
- make sure you assign the appropriate values to these variables
- clone this repository
- navigate to the **backend** folder and run **mvn spring-boot:run** 
- The API will be available at http://localhost:8080 (change 8080 with the port you set in your environment variable) 

To open the API documentation locally go to API_URL/swagger-ui.html

##### Web application
You can find the source code for the web application in the **frontend** directory. It is a ReactJS application. To set up and start the web application locally: 

- make sure you have **git** and **Node** installed 
- clone this repository 
- navigate to the **frontend** folder and run **npm install** to install the application dependencies 
- create a **.env** in the same folder with the following variables

```javascript
REACT_APP_API_URL=
PORT=
```
- make sure you assign the variables its appropriate values (url at which your server is hosted)
- your application will run on port 3000 by default; if you want to change this configure the variable above, otherwise you can omit the variable 
- run **npm start** to start the application (it will run the app in development mode) 
- To view the application in your browser open http://localhost:3000 (or your port of choice)
