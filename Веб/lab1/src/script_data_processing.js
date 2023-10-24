import { addPoint } from "./canvas.js";

export function sendAndDisplayData(R, X, Y) {
    // Создаем объект с данными для отправки
    const data = {
        R: R,
        X: X,
        Y: Y
    };

    // Опции для POST-запроса
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // Устанавливаем заголовок для JSON-данных
        },
        body: JSON.stringify(data) // Преобразуем данные в формат JSON
    };

    // Отправляем POST-запрос на сервер
    fetch('src/server.php', options)
        .then(response => response.json()) // Разбираем JSON-ответ, если сервер отвечает JSON
        .then(result => {
            const tableBody = document.querySelector('.results-table tbody');
            const newRow = tableBody.insertRow();
            newRow.insertCell().textContent = result.R;
            newRow.insertCell().textContent = result.X;
            newRow.insertCell().textContent = result.Y;
            addPoint(result.R, result.X, result.Y);
            newRow.insertCell().textContent = result.Result;
            newRow.insertCell().textContent = result.CurrentTime;
            newRow.insertCell().textContent = result.ScriptExecutionTime;
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

const url = 'https://se.ifmo.ru/~s367278/lab1/src/server.php';

const data = {
  R: '4',
  X: 'вравамва',
  Y: '2',
};

fetch(url, {
  method: 'POST',
  headers: {
    'Accept': '*/*',
    'Accept-Language': 'ru,en;q=0.9',
    'Content-Type': 'application/json',
    'cookie': 'COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=ru_RU; ...', // Добавьте сюда вашу куку
    'origin': 'https://se.ifmo.ru',
    'referer': 'https://se.ifmo.ru/~s367278/lab1/index.html',
    'sec-ch-ua': '"Chromium";v="116", "Not A;Brand";v="24", "YaBrowser";v="23"',
    'sec-ch-ua-mobile': '?0',
    'sec-ch-ua-platform': 'Windows',
    'sec-fetch-dest': 'empty',
    'sec-fetch-mode': 'cors',
    'sec-fetch-site': 'same-origin',
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.967 YaBrowser/23.9.1.967 Yowser/2.5 Safari/537.36',
  },
  body: JSON.stringify(data),
})
  .then(response => response.json())
  .then(result => {
    console.log(result);
  })
  .catch(error => {
    console.error('Error:', error);
  });

