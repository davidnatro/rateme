# Rate me

### Описание

Данный API предоставляет набор методов и функций, ориентированных на решение алгоритмических задач.
В рамках курсовой работы был реализован MVP со следующим функционалом:

1. Регистрация и авторизация пользователей
2. Управление пользовательскими ролями
3. Управление компаниями
    * CRUD операции
    * Найм и увольнение сотрудников
    * Создание и удаление контестов/задач сотрудниками компании
4. Создание комнат для совместного решения задач
5. Наиболее запрашиваемые задачи хранятся в кеше (redis)
6. Интеграция с judge0 (проверка решений)

### Стек

* Spring Boot
* Postgres
* Redis
* Spring Cloud Eureka
* Spring Cloud Gateway
* Hashicorp Vault

### Схема базы данных

* [Схема](/architecture/db/diagram.pdf)

### Архитектура

* [Текущая](/architecture/current/rateme.drawio.pdf)
* [Планируемая](/architecture/future/rateme.drawio.pdf)
