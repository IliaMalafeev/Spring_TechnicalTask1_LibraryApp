# LibraryApp

Project is done as a technical task example.

Stack: Java, Spring Core, Spring MVC, JdbcTemplate, Spring Validator, PostgreSQL, Thymeleaf.

## Technical Task

Local library wants to implement digital accounting of books. 
You get the task to develop a web-application for that purpose.
Librarians want to have the functionality of registering clients, 
issue books for them and unbind books (when the client returns the book to the library).

Approximate time to complete the task: 5-10 hours.

DataBase must contain 2 tables (entities) - `Person` and `Book`.
For both tables `id` should be autogenerated.

### Required functionality
* Page for adding, updating and deleting a `Person`
* Page for adding, updating and deleting a `Book`
* Page with the list of all people
  * Each `Person` is clickable - leads to person specific page.
* Page with the list of all books
    * Each `Book` is clickable - leads to book specific page.
* `Person` page - shows person entity info and a list of books 
currently bound to this person. If this person did not take any books yet,
message "This person did not take any books yet." should be displayed 
instead of the books list 
* `Book` page - shows book entity info and a name of the person, who currently 
has this book. If this book is currently free, message "This book is currently free." 
should be displayed instead
  * On this page, if the book is bound to someone, near to the persons name 
  there must be a button "Unbind Book". This button is clicked by librarian when 
  person returns the book to the library. When clicked, the book becomes 
  free to take again and disappears from persons books list.
  * On this page, if the book is free, there must be a dropdown list (`<select>`) 
  with all persons and a button "Assign Book". This button is clicked by librarian 
  when someone wants to take the book home. When clicked, book must become assigned 
  to that person and appear in persons books list.
* All fields must be validated with `@Valid` and `Spring Validator` if required.


## Implementation

Source code in this repository contains all required configurations and functionality, 
as well as entities, controllers, validators, etc. It is a fully complete functional project.

If you want to run and test it - you have to create a test database on your local machine 
and fill out missing fields in database.properties file 
(do not forget to remove ".origin" from file name). I have also added sql script file
"postgres_table_creation_commands.sql" with commands to create the necessary tables.

**Tomcat** server or any other local server should be connected manually since the scope 
here is not to use SpringBoot functionality.
