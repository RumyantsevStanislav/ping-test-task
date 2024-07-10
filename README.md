Запуск:

- в docker-compose изменить переменные окружения, при необходимости, на свои.
  - postgres
      - POSTGRES_DB
      - POSTGRES_USER
      - POSTGRES_PASSWORD
  - ping
      - POSTGRES_URL
      - POSTGRES_USER
      - POSTGRES_PASSWORD
- Запустить docker-compose.

Изменения, которые внёс бы, имея больше времени на выполнение:

- Поле address в БД вынести в отдельную талицу с двумя полями ip и domain и связью один ко многим с таблицей executions
- Валидация пришедшего с фронта домена или ip на соответствие паттернам.
- Валидация сохраняемой сущности hibernate.
- Глобальная обработка исключений через ControllerAdvice, расширение количества обрабатываемых исключений (например,
  потеря соединения с БД)
- Местами разнести методы по разным классам, например CommandExecutorImpl.interpretResult и
  CommandExecutorImpl.executeCommand
- TimerExecutor кривоват, успел только "заставить работать"
- Использование Future в асинхронных задачах.
- UI - работает только выполнение команды. 
Чтобы хоть как-то сгладить провал по UI - ссылка на мой пет-проект на Angular https://github.com/RumyantsevStanislav/book-find.ru/tree/develop/frontend