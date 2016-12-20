# library
© Milashevskaya Ekaterina
Training project LIBRARY in IT academy (Java Developer program)
=============================
Project has a three-tier architecture.<br>
Build-utility - Maven.<br>
Frameworks - Hibernate, Spring, Apache Tiles.<br>
DB - MySQL, driver - JDBC. <br>
Simple JUnit is presented for dao and services tiers.<br>
View - JSP (JSTL/EL), i18n.<br>
Security - Spring Security.


Structure
------------
You can see the following directories:

      entities/      All entities are involved in the functioning of the library
      dao/           The classes implement connection and CRUD operation with the database check the validity
      services/      Services checkes the validity of data before sending to dao and form the extracted data for web application
      web/           Controllers, Filters and Coontrollers for processing request and response, and generation view through JSP+Tiles

How does it work?
------------
There are two roles – the user (reader) and the admin.
### user
The user can search the books by the title or its part and he/she can make the order from the resulting list. Also, the user can see all their own orders and all books which he/she has in use for current time. In the list of orders, the user can cancel orders, which are not needed any more.
### admin
The admin can search the books by the title or its part. The admin can see the list with all orders in the library and the same list only for a concrete reader by the user's ID. From the order list the admin can give the book to the user for reading. In addition, the admin can see the list of books, which are read by a concrete reader (by user's ID) and cancel the book from this list when the user returns it. The admin can add books and delete them. The admin can see the list of books, which are not returned in time, and put the user in the blacklist for not returning the books.
