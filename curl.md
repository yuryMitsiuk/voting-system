# CURL Commands

---
## User:

- #### Get profile

    `curl -s http://localhost:8080/rest/profile -u user@gmail.com:password`

- #### Update profile

    `curl -s -X PUT -d '{"id":100000,"name":"updateUser","email":"updateuser@gmail.com","password":"updatePassword","roles":["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/profile -u user@gmail.com:password`

- #### Delete profile

    `curl -s -X DELETE http://localhost:8080/rest/profile/100000 -u user@gmail.com:password`

- #### Get all restaurants

    `curl -s http://localhost:8080/rest/profile/restaurants -u user@gmail.com:password`

- #### Get the restaurant(id=100005) menu

    `curl -s http://localhost:8080/rest/profile/restaurants/100005/menu -u user@gmail.com:password`

- #### Vote for the restaurant(id=100004)

    `curl -s -X POST -d '{"restaurant":{"id":100004,"name":"Perfetto"}}' -H 'Content-Type: application/json' http://localhost:8080/rest/profile/vote -u user@gmail.com:password`

---
## Admin:

###  OPERATIONS on users

- #### Get all users

    `curl -s http://localhost:8080/rest/admin/users -u admin@yandex.ru:password`

- #### Get the user (id = 100000)

    `curl -s http://localhost:8080/rest/admin/users/100000 -u admin@yandex.ru:password`

- #### Create new User

    `curl -X POST -d '{"name":"newUser","email":"newEmail@yahoo.com","password":"newPassword","roles":["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080rest/rest/admin/users -u admin@yandex.ru:password`

- #### Delete the user (id = 100028)

    `curl -s -X DELETE http://localhost:8080/rest/admin/users/100028 -u admin@yandex.ru:password`


- #### Get user by email (user@gmail.com)

    `curl -s http://localhost:8080/rest/admin/users/by?email=user@gmail.com -u admin@yandex.ru:password`

- #### Get all votes of the user (id = 100000)

    `curl -s http://localhost:8080/rest/admin/users/vote?userId=100000 -u admin@yandex.ru:password`


### OPERATIONS on restaurants

- #### Get All restaurants

    `curl -s http://localhost:8080/rest/admin/users -u admin@yandex.ru:password`

- #### Get the restaurant (id = 100005)

    `curl -s http://localhost:8080/rest/admin/restaurants/100005 -u admin@yandex.ru:password`


- #### Create new restaurant

    `curl -X POST -d '{"name":"newRestaurant"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants -u admin@yandex.ru:password`

- #### Update the restaurant

    `curl -X PUT -d '{"id":100029,"name":"newRestaurantUpdated"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants -u admin@yandex.ru:password`

- #### Delete the restaurant (id = 100029)

    `curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100029 -u admin@yandex.ru:password`

- #### Get the restaurant by name (Falcone)

    `curl -s http://localhost:8080/rest/admin/restaurants/by?name=Falcone -u admin@yandex.ru:password`

- #### Get all votes of the restaurant (id = 100003)

    `curl -s http://localhost:8080/rest/admin/restaurants/vote?restaurantId=100003 -u admin@yandex.ru:password`


### OPERATIONS on dishes


- #### Get menu(dishes) of the restaurant (id = 100005)

    `curl -s http://localhost:8080/rest/admin/restaurants/100005/menu -u admin@yandex.ru:password`

- #### Get dish (id = 100015) of the restaurant (id = 100005) 

    `curl -s http://localhost:8080/rest/admin/restaurants/100005/menu/100015 -u admin@yandex.ru:password`

- #### Create new dish of the restaurant (id = 100005)

    `curl -X POST -d '{"name":"newDish","price":2.25}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100005/menu -u admin@yandex.ru:password`

- #### Update the dish (id = 100030) of the restaurant (id = 100005)

    `curl -X PUT -d '{"id":100030,"name":"newDishUpdate","price":2.25}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100005/menu -u admin@yandex.ru:password`

- #### Delete the dish (id = 100031) from restaurant (id = 100005)

    `curl -X DELETE http://localhost:8080/rest/admin/restaurants/100005/menu/100031 -u admin@yandex.ru:password`

- #### Get all the dishes of the restaurant for all time

    `curl -s http://localhost:8080/rest/admin/restaurants/100005/menu/history -u admin@yandex.ru:password`
