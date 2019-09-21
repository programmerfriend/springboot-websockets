# Spring Boot Twitter WebSocket Stream

This is an example building a Twitter WebSocket Stream using Spring Boot and twitter4J.

Folder Structur:
* **twitter-backend:**<br/>
  Spring Boot Service (Java 11) providing a Websocket connection at ws://localhost:8080/tweets.
  It also serves an example JS Frontend at http://localhost:8080.

* **twitter-frontend:**<br/>
  An example Frontend written with ReactJS.
  It is getting served at http://localhost:4000.
  

## How to start the services:

Hava a look at: https://programmerfriend.com/spring-boot-websocket/ especially about configuring twitter4J to use your Twitter Account.