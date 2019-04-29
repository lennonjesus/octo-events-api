# octo-events-api

### A simple project to demonstrates how to receives Github issues webhooks, save to in-memory database and expose them with an endpoint 


```
PLUS: You can test it deploying on Heroku. =D
```
 

[![Build Status](https://travis-ci.org/lennonjesus/octo-events-api.svg?branch=master)](https://travis-ci.org/lennonjesus/octo-events-api)
[![Code Climate](https://codeclimate.com/github/lennonjesus/octo-events-api/badges/gpa.svg)](https://codeclimate.com/github/lennonjesus/octo-events-api)

### Requirements
You should have Git, Java, Gradle and Heroku-cli installed.


### Clone this repo
```
$ git clone https://github.com/lennonjesus/octo-events-api.git && cd octo-events-api 
```

### Test on local machine

#### Run local tests (it will download dependencies, compile sources and run tests)
```
$ gradle clean test
```

### Run on local machine

#### Start server (it will download dependencies, compile sources and start the server)
```
$ gradle run
```

#### Stop the server
```
$ gradle --stop
```

### Deploy and test on heroku

### Create new Heroku project
```
$ heroku create
```

### Deploy to Heroku
```
$ git push heroku master
```

### Scale up
```
$ heroku ps:scale web=1
```

#### Endpoints

### Receive Github issue events by webhook (read [here](https://developer.github.com/webhooks/) and [here](https://developer.github.com/webhooks/)) to configure it
```
POST /hooks/github
```

### Find events by issue internal id
```
GET /issues/[ISSUE-ID]/events
```

