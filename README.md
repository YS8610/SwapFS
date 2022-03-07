# SwapLesson App  
  
This is a simple web app for car and motorcycle learner to post request to swap lesson slots with other learners of the same school. It consists of a website for listing of swaps requests. Telegram bot is used by individual user to manage their swap request listings. The bot also serves as a messenger to inform user if there is any interested party for swapping of lesson slot.
  
  
## Setup  
  
There are 3 frameworks (Angular, Spring Boot and Python Flask ) used in this web application. mySQL database is used for storing data. The Angular, Spring boot, mySQL are served on digital ocean droplet and the python Flask are served on NameCheap Shared Hosting.  
  
  
## Angular  
  
Angular is used for front end rendering in browser. Use `ng build` to build the Angular files required for front end rendering in browser. The built files should be in `swapNG/dist/swap-ng`.  
  
  
## Spring Boot  
  
Spring Boot is used as server for api. The api endpoint are as below
1. /api/swaps - get all swaps in stored in mySQL database
2. /api/swap/{id} - get swap by id
3. /api/send - send telegam message to user identified via chatid
4. /api/add - add new swaps request to mySQL database
5. /api/chatid/{chatid} - get all swaps from chatid
6. /api/delete - delete swap id (**this api requires JWT token for access**)
  
Place all built Angular files into the static folder of Spring Boot folder. It should be located at `swap/src/main/resources/static`. The setting of the spring boot application is located at `swap/src/main/resources/application.properties`. Use `mvn clean package` to get the jar file from target folder(`swap/target`). The environment variable used is JWT_SECRET.  
  
  
## Python Flask for Telegram Bot  
  
Python Flask backend server is deployed for controlling telegram bot via webhook. The files used is an example for deployment in cPanel through WSGI. (I am using NameCheap shared Hosting.) The library used are python-telegram-bot, requests and flask. Run `pip install -r requirements.txt`. The `requirements.txt` file is in `python_Flask/requirements.txt`. The environment variable used is BOT_TOKEN and JWT_TFIP.  
  
Telegram bot commands are as below.  
1. /chatid - return the chatid of the user
2. /swaps - return all swaps listed by the user
3. /delete - return a list of swaps listed by the user as button for user to press and delete  
  
  
## mySQL  
  
The schema for mySQL database is located as a sql file (./schema.sql). There are 2 tables.