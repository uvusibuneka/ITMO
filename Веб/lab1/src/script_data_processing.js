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


// Функция для чтения данных из куки и построения таблицы
function loadTableDataFromCookies() {
    // Чтение данных из куки
    const cookieValue = document.cookie
        .split("; ")
        .find(row => row.startsWith("tableData="));

    if (cookieValue) {
        // Извлечение данных и преобразование из JSON
        const savedTableData = JSON.parse(cookieValue.split("=")[1]);

        // Построение таблицы на основе данных
        const tableBody = document.querySelector('.results-table tbody');
        tableBody.innerHTML = '';

        savedTableData.forEach(data => {
            const tableBody = document.querySelector('.results-table tbody');
            const newRow = tableBody.insertRow();
            newRow.insertCell().textContent = data.R;
            newRow.insertCell().textContent = data.X;
            newRow.insertCell().textContent = data.Y;
            addPoint(result.R, result.X, result.Y);
            newRow.insertCell().textContent = data.Result;
            newRow.insertCell().textContent = data.CurrentTime;
            newRow.insertCell().textContent = data.ScriptExecutionTime;
            saveTableDataToCookies();
        });
    }
}

// Вызов функции для загрузки данных из куки при загрузке страницы
document.addEventListener('DOMContentLoaded', function () {
    loadTableDataFromCookies();
});


// Функция для сохранения данных таблицы в куках
function saveTableDataToCookies() {
    const tableBody = document.querySelector('.results-table tbody');
    const tableData = [];

    // Извлечение данных из таблицы и сохранение их в массив
    const tableRows = tableBody.querySelectorAll('tr');
    tableRows.forEach(row => {
        const cells = row.querySelectorAll('td');
        if (cells.length === 3) {
            const R = cells[0].textContent;
            const X = cells[1].textContent;
            const Y = cells[2].textContent;
            tableData.push({ R, X, Y });
        }
    });

    // Преобразование данных в JSON и сохранение в куках
    document.cookie = "tableData=" + JSON.stringify(tableData);
}
