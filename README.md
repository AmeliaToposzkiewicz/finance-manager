# finance - manager 💸
> Simple app created with Java, Maven, MySQL, Hibernate and Lombok.

## Table of Contents
* [General Information](#general-information)
* [Features](#features)
* [Technologies Used](#technologies-used)
* [Setup](#setup)

## General Information
Using the application you can control your income and expenses, group expenses by created categories, check balance etc. 
All your data is stored in your MySQL database.

## Features 
1.  Creating new category
2.  Finding all categories
3.  Deleting category by id
4.  Creating new income
5.  Finding all incomes
6.  Deleting income by id
7.  Creating new outcome
8.  Finding all outcomes
9.  Deleting outcome by id
10. Finding outcomes between two dates
11. Grouping outcomes by category
12. Finding all outcomes and incomes
13. Checking balance

## Technologies Used
- Java 17
- Maven 4.0.0
- MySQL 8.0.32
- Hibernate 6.1.7.Final
- Lombok 1.18.26

## Setup
- Clone this repository.
- Make sure you are using JDK 17, Maven 4.x.
- Create MySQL database.
- Create environment variables:
   * DB_NAME=name_of_your_database
   * DB_USER=your_database_user
   * DB_PASSWORD=password_of_specified_user
- Create persistence.xml file in META-INF folder, using persistence.example.xml as example.
- In persistence.xml file:
   * replace url with url to your database
   * replace dbUser with your db user
   * replace dbPassword with password of specified user
   !!! Use the same data as when creating environment variables !!!
