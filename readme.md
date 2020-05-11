### **Votingsystem** [![Codacy Badge](https://api.codacy.com/project/badge/Grade/ff9756a17688470da55edd4dd202e249)](https://www.codacy.com/app/Gaiter/Votingsystem?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Gaiter/Votingsystem&amp;utm_campaign=Badge_Grade)

Task: Design and implement a REST API using Hibernate/Spring/SpringMVC without frontend.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

---

#### Used technologies
- Java 8
- Maven
- Spring Security
- Spring 5
  * Spring Core (Beans, Context)
  * Spring Data Access (JdbcTemplate, ORM, JPA (Hibernate), Transactions)
- DBs: H2
- RESTful services
- Spring Security Test / JUnit 5
- Tomcat

# CURL Commands

---
## Admin:

#### get All Users
`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

#### get User 100001
`curl -s http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

#### delete User 100001
`curl -s -X DELETE http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

#### create User
`curl -s -X POST -d '{"name":"User3","email":"user3@yandex.ru","password":"password","roles":["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

#### update User
`curl -s -X PUT -d '{"id":100001,"name":"Miha","email":"user2@yandex.ru","password":"password","roles":["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get All Dishes
`curl -s http://localhost:8080/rest/admin/dish --user admin@gmail.com:admin`

#### get Dish 100012
`curl -s http://localhost:8080/rest/admin/dish/100012 --user admin@gmail.com:admin`

#### delete Dish 100012
`curl -s -X DELETE http://localhost:8080/rest/admin/dish/100012 --user admin@gmail.com:admin`

#### create Dish
`curl -s -X POST -d '{"name":"Chezze","price":1000}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/dish?menuId=100009 --user admin@gmail.com:admin`

#### update Dish 100012
`curl -s -X PUT -d '{"id":100012,"name":"New Salad","price":1500}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/dish?menuId=100009 --user admin@gmail.com:admin`

#### create Menu
`curl -s -X POST -d '{"name":"Lunch","date":"2018-09-30"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/menu?restaurantId=100003 --user admin@gmail.com:admin`

#### update Menu 100009
`curl -s -X PUT -d '{"id":100009,"name":"Lite lunch","date":"2018-09-30"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/menu/ --user admin@gmail.com:admin`

#### get Menu 100009
`curl -s http://localhost:8080/rest/admin/menu/100009 --user admin@gmail.com:admin`

#### delete Menu 100009
`curl -s -X DELETE http://localhost:8080/rest/admin/menu/100009 --user admin@gmail.com:admin`

#### get All Menus
`curl -s http://localhost:8080/rest/admin/menu --user admin@gmail.com:admin`

#### create Restaurant
`curl -s -X POST -d '{"name":"Horses"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurant --user admin@gmail.com:admin`

#### update Restaurant 100003
`curl -s -X PUT -d '{"id":100003,"name":"New Mandarin"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurant --user admin@gmail.com:admin`

#### get Restaurant 100003
`curl -s http://localhost:8080/rest/admin/restaurant/100003 --user admin@gmail.com:admin`

#### delete Restaurant 100003
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurant/100003 --user admin@gmail.com:admin`

#### get All Restaurants
`curl -s http://localhost:8080/rest/admin/restaurant --user admin@gmail.com:admin`

---
## User:

#### get profile
`curl -s http://localhost:8080/rest/profile --user user@yandex.ru:password`

#### delete profile
`curl -s -X DELETE http://localhost:8080/rest/profile --user user@yandex.ru:password`

#### update profile
`curl -s -X PUT -d '{"id":100001,"name":"Antonio","email":"user2@yandex.ru","password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/profile --user user2@yandex.ru:password`

#### register new User
`curl -s -X POST -d '{"name":"User3","email":"user3@yandex.ru","password":"password","roles":["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/register`

#### get All menus of current day with restaurants and dishes for vote
`curl -s http://localhost:8080/rest/profile/menus --user user@yandex.ru:password`

#### vote for restaurant 100004
`curl -s -X POST http://localhost:8080/rest/profile/voting?restaurantId=100004 --user user@yandex.ru:password`
