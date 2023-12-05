import {isRValid, isXValid, isYValid, validateData} from "./script_validation.js";
import { clearGraph } from "./canvas.js";
import { addPoint } from "./canvas.js";

function XtoInput() {
    const X = document.querySelector('#Xselection').value;
    document.querySelector('#Xinput').value = X;
    updateDisable();
    if (!isXValid(X)) {
        document.getElementById('errorX').style.display = 'block';
    } else {
        document.getElementById('errorX').style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('#clear').addEventListener('click', clearTableHandler);
    document.querySelector('.btn-process').addEventListener('click', submit);
    document.querySelector('#Xselection').addEventListener('change', XtoInput);
    document.querySelector('#Yinput').addEventListener('change', updateY);
    document.querySelector('#Rinput').addEventListener('change', updateR);
    const canvas = document.querySelector('canvas');
    const context = canvas.getContext('2d');
    canvas.addEventListener('click', function (event) {
        let rect = canvas.getBoundingClientRect();
        let x = event.clientX - rect.left;
        let y = event.clientY - rect.top;
        console.log('X: ' + x + ', Y: ' + y);
        let R = document.querySelector('#Rinput').value;
        if(R === ''){
            alert('Please, enter R');
            return;
        }else {
            let X = (x - 150) / 120 * R;
            let Y = (150 - y) / 120 * R;
            //precision to 12 digits;
            X = Math.round(X * 100000000) / 100000000;
            Y = Math.round(Y * 100000000) / 100000000;
            console.log("X: " + X + " Y: " + Y + " R: " + R);
            document.querySelector('#Xinput').value = X;
            if (!isXValid(X)) {
                document.getElementById('errorX').style.display = 'block';
            } else {
                document.getElementById('errorX').style.display = 'none';
            }
            document.querySelector('#Yinput').value = Y;
            updateX();
            updateY();
            addPoint(R, X, Y);
            document.querySelector("#submit").click();
        }
    });
});




function updateDisable() {
    const R = document.querySelector('#Rinput').value;
    const X = document.querySelector('#Xinput').value;
    const Y = document.querySelector('#Yinput').value;
    //document.getElementById('clear').value = 0;
    if (X !== '' && Y !== '' && R !== '' && isRValid(R) && isXValid(X) && isYValid(Y)) {
        document.querySelector('.btn-process').disabled = false;
    } else {
        document.querySelector('.btn-process').disabled = true;
    }

}


function updateX() {
    const X = document.querySelector('#Xinput').value;
    updateDisable();
    if (!isXValid(X)) {
        document.getElementById('errorX').style.display = 'block';
        //round X to int
    } else {
        document.getElementById('errorX').style.display = 'none';
        let selectElement = document.getElementById('Xselection');

        for (let i = 0; i < selectElement.options.length; i++) {
            let option = selectElement.options[i];

            if (parseInt(option.value) === Math.round(X)) {
                option.selected = true;
                break;
            }
        }
    }
}

function updateY() {
    const Y = document.querySelector('#Yinput').value;
    updateDisable();
    if (!isYValid(Y)) {
        document.getElementById('errorY').style.display = 'block';
    } else {
        document.getElementById('errorY').style.display = 'none';
    }

}

function updateR() {
    const R = document.querySelector('#Rinput').value;
    updateDisable();
    if (!isRValid(R)) {
        document.getElementById('errorR').style.display = 'block';
    } else {
        document.getElementById('errorR').style.display = 'none';
    }
}

function clearTableHandler() {;
    const tableBody = document.querySelector('#result-table');
    tableBody.innerHTML = '    <tr>\n' +
        '        <th>X</th>\n' +
        '        <th>Y</th>\n' +
        '        <th>R</th>\n' +
        '        <th>Result</th>\n' +
        '        <th>Current Time</th>\n' +
        '        <th>Execution time</th>\n' +
        '    </tr>';
    clearGraph();
}

function submit(){
    const R = document.querySelector('#Rinput').value;
    const X = document.querySelector('#Xselection').value;
    const Y = document.querySelector('#Yinput').value;
    if(!validateData(R, X, Y)){
        updateDisable();
        updateX();
        updateY();
        updateR();
        return;
    }
}
