import {validateData} from "./script_validation.js";
import { sendAndDisplayData } from "./script_data_processing.js";
import { clearGraph } from "./canvas.js";
document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.btn-process').addEventListener('click', processButtonHandler);

    document.querySelector('.btn-clear').addEventListener('click', clearTableHandler);
});

function processButtonHandler() {
    const R = document.querySelector('#Rinput').value;
    const X = document.querySelector('#Xselection').value;
    const Y = document.querySelector('#Yinput').value;

    if (validateData(R, X, Y)) {
        sendAndDisplayData(R, X, Y);
    } else {
        alert('Invalid data. Please check your input.');// поменяй это на  адекватное сообщение об ошибке
    }
}

function clearTableHandler() {
    clearGraph();
    const tableBody = document.querySelector('.results-table tbody');
    tableBody.innerHTML = ''; 
}
