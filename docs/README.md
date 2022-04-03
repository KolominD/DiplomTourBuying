# Дипломный проект

## Окружение : 

* Windows 10 21H1
* Docker
* IntelliJ IDEA Ultimate

Запуск: 
* Склонировать проект
 
* В терминале ввести команду 

`docker-compose up -d`

* Для использования mySQL запустить приложение командой 

`java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app`

* Для использования postgreSQL запустить приложение командой 

`java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres`


* Для запуска тестов и их итераций в gradle запустить команду `gradle clean test`
