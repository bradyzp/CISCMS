CIS CMS Readme
==============

CIS 4050 Group Project - Spring 2018

Group 'No shirt, no shoes, no service' (nsnsns)

Overview:

- CIS CMS is a Spring Boot Web MVC application, targeting the JAVA 11 Runtime
- The application is built using the Gradle build system, this allows for easy development setup and dependency resolution
- Primary project sources are found within src/main/java/net/nsnsns/ciscms/
- Resources are found within the src/main/resources - this includes HTML templates, images, css, javascript etc


Source Structure:

Within the primary project sources folder (src/main/java/net/nsnsns/ciscms/), classes are organized generally as follows:

- **controllers**: The controllers are the brains behind each rendered page that the user views, they are responsible for 
selecting and serving the html template to the user, based on the path, and the request type i.e. GET/POST
- **security**: Security related configurations - allowing user to log in and preventing anonymous viewers from accessing 
private data
- **models**: Models are the Java representation of database entities, together with the services and repos classes, these 
form the core of the data persistence (storage in the DB) layer.
- **repos**: Repository interfaces which provide a means to retrieve or save models (entities) to the database
- **services**: Service classes are the go-between between the repositories and controllers, controllers will use the service 
classes to request entities (e.g. a Course entity) from the database, or to save a new comment or syllabus entity. 
Services internally make use of the repository interfaces to interact with the database.
