API Endpoints

1. GET      /api/v1/health
2. POST     /api/v1/login
3. POST     /api/v1/uploadTemplateMapping
4. POST     /api/v1/convertMt202ToPacs009Format

//TODO 
5. POST     /api/v1/convertPacs009ToMt202Format
6. GET      /api/v1/getTemplateMappings
7. GET      /api/v1/getTemplateMapping/:clientId

8. POST     /api/v1/register
9. GET      /api/v1/logout
10. POST    /api/v1/createBypassAuthAPI
11. GET     /api/v1/getBypassAuthAPI
12. POST    /api/v1/createWhitelistAPI
13. POST    /api/v1/updateWhitelistAPI
14. GET     /api/v1/getWhitelistApi

API can be tested by chaning the path configuration in the code  
   1. GET      /api/v1/health
   2. POST     /api/v1/login
   3. POST     /api/v1/uploadTemplateMapping
   4. POST     /api/v1/convertMt202ToPacs009Format

  

Pending tasks

1. log4j implementation
2. MySQL DB tables and integration with APIs
3. Email Notifications on alerts
4. All template & mapping configuration should be on API level
5. Secure APIs and exceptional handling. 
6. Docker integration.
7. Auto Deployment scripts. 
8. Expose APIs to external users.
9. Implement SSO login
10. Configuring  certificates for HTTPS 
11. store secure variables in Application ENV. 



******************************************************
To start the application 
******************************************************
From the project root folder , execute the below commands. 
$ git clone <repo>>
$ cd <project-name>

"To build compile and install all dependencies"
$ ./infy-build.sh   ==> runs on linux environement
$ ./infy-start.sh   => start the service  http on localhost 8080

**********************************************************
API TESTING
**********************************************************
curl http://localhost:8080/api/v1/health
