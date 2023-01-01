# Диздок нового сервиса для проверки попаданий в заданную область

## Проблема. Зачем нам нужен этот сервис?

До конца семестра остался месяц, а по вебу так и не сделаны
все лабы - осталось 2 штуки: жаваеееееее и последняя на
фреймворках для пусечек. Кроме веба есть еще множество других
предметов, нагрузка по которым бьет даже больше, чем веб.

Так что было принято решение в кратчайшие сроки разработать
абсолютно иновационный сервис, проверяющий попадания точек
в заданную область.

Цена ошибки очень велика, на кону счастливая студенческая
жизнь.

## Функционал

`Tapari` - так я это детище -
должен поддерживать следующие фичи:
- регистрация пользователей
- создание/удаление картинок из примитивов
  (многоугольник, эллипс)
  c помощью комбинаторов
  (объединение, пересечение, отрицание).
- отдельные части фигурок имеют цвет
- фигурки принадлежат пользователям
- фигуры имеют абсолютные координаты
  (относительно корневого канваса)
- пользователь может "тыкнуть" в рандомную
  точку картинки и узнать,
  попал он в заданную ею область
  (мн-во точек R^2) или нет
- история "тык"ов сохраняется
- зыписи "тык"ов можно получать,
  удалять и косвенно создавать

## HTTP API

### api/ops

```
GET  api/ops/ping
Всегда отвечает "pong". Нужно для первой 
проверки работоспособности сервиса.
CODE 200 - сервис запущен
     4XX - все плохо
     5XX - все плохо
```

### api/users

```
GET  api/users/getById?
       id=*user-id*
Возвращает информацию о пользователе 
по его идентификатору в системе.
CODE 404 - пользователь 
с таким айдишником не найден

POST api/users/create
{
    "role": "*admin,customer*",
    "login": "*user-login*",
    "password": "*user-password*"
}
Создает пользователя с логином 
(никнеймом) *user-login* и 
паролем *user-password*.
CODE XXX - пользователь с таким логином уже существует
```

### api/pictures
```
GET  api/pictures/getById?
       id=*picture-id*
Возвращает картинку с переданным айдишником.
CODE 404 - картинка не найдена

GET  api/pictures/getByFullName?
       owner_id=*owner-id*&
       name=*picture-name*
Находит картинку в "папке" ее владельца 
по ее имени.
CODE 404 - пользователь/картинка не найден(а)

POST api/pictures/create
{
    "owner": *owner-id*,
    "name": "*picture-name*",
    "content": {
        "figure": {
            ...
        }
    }
}
Создает картинку и возвращает ее айдишник
в случае успеха.
CODE - лень описывать
```

### api/picture/taps

```
POST api/picture/taps/create
{
    "user_id": *user-id*,
    "picture_id": *picture_id*,
    "position": {
        "x": *tap-x*,
        "y": *tap-y*
    }
}
RETURN
{
    "id": *tap-id*,
    "user_id": *user-id*,
    "picture_id": *picture_id*,
    "time": *timestamp*,
    "hit": true/false
}

GET  api/picture/taps/getAllByUserId?
       user_id=*user-id*&
       ordered_by=*ordering (time, id)*&
       from=*left-index inclucive*&
       to=*right-index exclucive*
RETURN
{
    "from": *left-index inclucive*,
    "to": *right-index exclucive*,
    "total": *total-taps-count*,
    "taps": [...]
}

DELETE api/picture/taps/deleteAllInCircle?
         x=*circle-center-x*&
         y=*circle-center-y*&
         r=*circle-radius*
RETURN
{
    "deleted_taps": [...]
}
```

### api/auth
```
POST api/auth/getAccessToken
{
    "login": "*user-login*",
    "password": "*user-password*"
}
```

## Data model