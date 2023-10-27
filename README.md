# softwa

1.Я начинаю с установки API ключа для OpenWeatherMap. Этот ключ служит идентификатором вашего приложения и предоставляет доступ к данным о погоде через API OpenWeatherMap.

private static final String API_KEY = "31314b4fa0621986d2a0d8361009365e";

2.Получение пользовательского ввода:

Я использую BufferedReader для сбора ввода от пользователя. Программа выводит сообщение "Enter city name: " и ожидает ввода названия города от пользователя.

BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter city name: ");
String city = reader.readLine();

3.Создание URL для API запроса и отправка запроса:

Я формирую URL для API запроса, включая введенное название города и мой API ключ. Затем я устанавливаю соединение с API OpenWeatherMap, отправляя запрос для получения данных о погоде для указанного города.

String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
URL url = new URL(apiUrl); 
HttpURLConnection connection = (HttpURLConnection) url.openConnection();

4.Получение и обработка ответа от API:

После отправки запроса, я проверяю ответ от сервера. Если ответ успешен (HTTP статус код 200), программа читает и обрабатывает JSON данные, полученные от API OpenWeatherMap.

int responseCode = connection.getResponseCode(); if (responseCode == HttpURLConnection.HTTP_OK) { 
// Parsing the JSON response // ... } 
else { System.out.println("Failed to retrieve weather data for the city: " + city); }

5.Обработка JSON данных и вывод результатов:

Я извлекаю необходимую информацию из JSON объекта, такую как температура, влажность, скорость ветра и другие параметры. Затем я преобразую эти данные в удобочитаемый формат (например, из Кельвинов в Цельсии) и выводлю результаты в консоль.

JSONObject jsonResponse = new JSONObject(response.toString()); JSONObject main = jsonResponse.getJSONObject("main"); JSONObject wind = jsonResponse.getJSONObject("wind"); JSONObject clouds = jsonResponse.getJSONObject("clouds");
// Extracting and converting data // ...
System.out.println("Temperature: " + temperatureCelsius + "°C"); // Outputting other data


6.Обработка ошибок:

Весь код находится в блоке try-catch для обработки возможных ошибок, которые могут возникнуть в процессе выполнения программы. Если происходит исключение, программа выводит сообщение об ошибке в консоль.

} catch (Exception e) {
e.printStackTrace(); }

Этот код выполняет все таски которые были в задание.

URL (Uniform Resource Locator): URL - это адрес ресурса в интернете. В этом коде я создаю URL, который представляет собой адрес API OpenWeatherMap,
куда отправляются наши запросы для получения данных о погоде. URL включает в себя название города, введенное пользователем, и мой API ключ.

JSON (JavaScript Object Notation): JSON - это формат обмена данными, который понятен как человеку, так и компьютеру.
В контексте этого кода я использую JSON для обработки ответа, который мы получаем от API OpenWeatherMap. 
Мы парсим (извлекаем данные из) JSON объекта, чтобы получить информацию о погоде для введенного города.

HTTP (Hypertext Transfer Protocol): HTTP - это протокол передачи данных в интернете.
В этом коде я использую HTTP для отправки запросов к API OpenWeatherMap и получения ответов с сервера. 
HTTP позволяет нашему приложению взаимодействовать с удаленным сервером и получать актуальные данные о погоде.
