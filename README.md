# Задание 19. Создать веб-службы location и weather.
* Задание. Создать веб-службы location и weather.
Сервис Location должен хранить данные о городе, в котором проживает Person. Сервис Weather в свою очередь должен возвращать данные о погоде по координатам (Можно реализовать, как запрос к БД. Интеграция с сервисом погоды, будет реализована в след. заданиях).
Подумайте, какие зависимости нужно использовать для этих служб.

# Задание 20. Оптимизируйте запросы.
* Задание. Реализуйте кэширование данных на одну минуту, чтобы не обращаться при каждом запросе на сервер api.openweathermap.org.

* Требования к API:
GET /weather?lat=&lon= - получить данные о погоде Root (lon - долгота, lat - широта)
* Структура классов:

```
class Clouds{
    int all;
}

class Coord{
    double lon;
    double lat;
}

class Main{
    double temp;
    double feels_like;
    double temp_min;
    double temp_max;
    int pressure;
    int humidity;
    int sea_level;
    int grnd_level;
}

class Root{
    Coord coord;
    ArrayList weather;
    String base;
    Main main;
    int visibility;
    Wind wind;
    Clouds clouds;
    int dt;
    Sys sys;
    int timezone;
    int id;
    String name;
    int cod;
}

class Sys{
    String country;
    int sunrise;
    int sunset;
}

class Weather{
    int id;
    String main;
    String description;
    String icon;
}

class Wind{
    double speed;
    int deg;
    double gust;
}

```

* Требования к названиям классов:
```
Model:
{Корневой пакет}.weather.model.Clouds.class
{Корневой пакет}.weather.model.Coord.class
{Корневой пакет}.weather.model.Main.class
{Корневой пакет}.weather.model.Root.class
{Корневой пакет}.weather.model.Sys.class
{Корневой пакет}.weather.model.Weather.class
{Корневой пакет}.weather.model.Wind.class
Controller:
{Корневой пакет}.weather.controller.WeatherController.class
Config:
{Корневой пакет}.weather.config.WeatherConfig.class
Main:
{Корневой пакет}.weather.WeatherApplication.class
```

№ Задание 21. Создание микросервиса location.
* Задание 1: Исправить в контроллере: Geodata geodata = repository.findByName(location).get(); RestTemplate restTemplate = new RestTemplate(). Это необходимо сделать для всех сервисов (Person, Location, Weather);
* Задание 2: Настроить приложение так, чтобы БД сохранялась в файле, а не в оперативной памяти. Это необходимо сделать для сервисов Person, Location.
* Задание 3: Добавить новые методы API, как указано ниже

Требования к API:
```
GET /location - Получить все List<Location>
GET /location?name={name} - Получить Location по name
POST /location - Добавить новый Location
PUT /location?name={name} - Изменить Location по name
DELETE /location?name={name} - Удалить Location по name
GET /location/weather?name={name} - Получить погоду для Location по name
```
Location:
```
class Location{
  Double longitude;
  Double latitude;
  String name;
}
```
Требования к названиям классов:
Model:
```
{Корневой пакет}.location.model.Weather.class
{Корневой пакет}.location.model.Location.class
```
Controller:
```
{Корневой пакет}.location.controller.LocationController.class
```
Config:
```
{Корневой пакет}.location.config.LocationConfig.class
```
Repository:
```
{Корневой пакет}.location.repository.LocationRepository.class
```
Main:
```
{Корневой пакет}.location.LocationApplication.class
```
