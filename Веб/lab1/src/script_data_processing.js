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
