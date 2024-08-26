# job4j_todo

## Project Description

A simple task management application. This application demonstrates the use of the technology stack including Spring
Boot, Thymeleaf, Bootstrap, Hibernate, and PostgreSQL. The task list can be useful for creating tasks, viewing the list
of tasks, modifying tasks, and tracking their status.

## Technology Stack

- Java 17
- PostgreSQL
- springframework.boot 2.7.6
- Hibernate
- Lombok
- H2database
- Liquibase

## Requirements for the environment
- Java 17 
- PostgreSQL 15.2
- Apache Maven 3.8.4

## Running the Project

- Create a database named 'todo' in PostgreSQL. 
- Check the database connection settings in the following files:
- - db/liquibase.properties 
- - src/main/resources/hibernate.cfg.xml 
- Before starting, run the command liquibase:update.
- Run the main method. -Open the browser at http://localhost:8080/.

### All tasks

<image src="https://github.com/AlenaAgeeva/job4j_todo/blob/master/screenshots/img.png" alt="All tasks" />

### Filtered tasks

<image src="https://github.com/AlenaAgeeva/job4j_todo/blob/master/screenshots/img_1.png" alt="Filtered tasks" />

<image src="https://github.com/AlenaAgeeva/job4j_todo/blob/master/screenshots/img_2.png" alt="Filtered tasks" />

### Adding a new task

<image src="https://github.com/AlenaAgeeva/job4j_todo/blob/master/screenshots/img_3.png" alt="Adding a new task" />

### Editing a task

<image src="https://github.com/AlenaAgeeva/job4j_todo/blob/master/screenshots/img_4.png" alt="Editing a task" />
