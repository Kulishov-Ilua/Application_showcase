# Application_showcase
## Материалы:
1. Видео: https://disk.yandex.ru/d/FYXVMeFjI_G53w
## Как запустить:
1. Развернуть postgre (таблицы будут созданы при первом запуске сервера)
2. В ресурсах сервера в application.yaml указать параметры:
   ktor:
   application:
   modules:
      - ru.kulishov.application_showcase.ApplicationKt.module
    deployment:
    port: 3000
    db:
    url: "jdbc:postgresql://localhost:5432/Имя БД"
    user: "Пользователь"
    pass: "Пароль"
    pool: 2
3. Запустить сервер
4. Добавить данные используя маршруты из AppRouting, CategoryRouting, MediaRouting
5. Собрать и запустить клиентскую часть