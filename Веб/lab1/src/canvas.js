
function drawGraph(){
    if (canvas.getContext) {
        const ctx = canvas.getContext('2d');

        const canvasWidth = canvas.width;
        const canvasHeight = canvas.height;

        const centerX = canvasWidth / 2;
        const centerY = canvasHeight / 2;
        const R = (Math.min(canvasHeight, canvasWidth) / 2) * 0.9;

        ctx.beginPath();
        ctx.strokeStyle = 'blue';
        ctx.fillStyle = 'red';
        ctx.fill();
        ctx.moveTo(centerX - canvasWidth / 2, centerY);
        ctx.lineTo(centerX + canvasWidth / 2, centerY);
        ctx.moveTo(centerX, centerY - canvasHeight / 2);
        ctx.lineTo(centerX, centerY + canvasHeight / 2);
        ctx.stroke();

        
        const Rdiv2 = R / 2;
        const marks = new Map();
        marks.set("-R", -R);
        marks.set("-R/2", -Rdiv2);
        marks.set("R", R);
        marks.set("R/2", Rdiv2);

        for (const [label, val] of marks) {
            const x = centerX + val;
            ctx.moveTo(x, centerY - 5);
            ctx.lineTo(x, centerY + 5);

            const y = centerY - val;
            ctx.moveTo(centerX - 5, y);
            ctx.lineTo(centerX + 5, y);

            ctx.fillText(label, x - 10, centerY + 15);
            ctx.fillText(label, centerX + 10, y + 5);


        }
        ctx.strokeStyle = 'blue';
        ctx.stroke();

        ctx.fillStyle = 'rgba(0, 0, 255, 0.5)'; // Задайте цвет заливки треугольника

        ctx.beginPath();
        ctx.moveTo(centerX - R/2, centerY); // Перемещаемся в первую точку
        ctx.lineTo(centerX, centerY - R); // Рисуем линию ко второй точке
        ctx.lineTo(centerX, centerY); // Рисуем линию ко третьей точке
        ctx.closePath(); // Замыкаем путь, чтобы создать треугольник
        ctx.fill(); // Заливаем треугольник указанным цветом

        //ctx.fillStyle = 'blue';

        ctx.beginPath();
        ctx.moveTo(centerX - R/2, centerY); // Перемещаемся в первую точку
        ctx.lineTo(centerX - R/2, centerY + R)
        ctx.lineTo(centerX, centerY + R); // Рисуем линию ко второй точке
        ctx.lineTo(centerX, centerY); // Рисуем линию ко третьей точке
        ctx.closePath(); // Замыкаем путь, чтобы создать треугольник
        ctx.fill(); // Заливаем треугольник указанным цветом

        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, R/2, -Math.PI/2, 0);
        ctx.closePath(); // Замыкаем путь, чтобы создать треугольник
        ctx.fill(); // Заливаем треугольник указанным цветом

        
    } else {
        console.error('Canvas is not supported in this browser.');
    }
}

export function addPoint(R, X, Y) {
    const ctx = canvas.getContext('2d');
    const Rstandard = (Math.min(canvas.height, canvas.width) / 2) * 0.9;
    const Xmodif = X * (Rstandard / R);
    const Ymodif = Y * (Rstandard / R);

    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    const x = centerX + Xmodif;
    const y = centerY - Ymodif;

    
    ctx.beginPath();
    ctx.arc(x, y, 3, 0, 3 * Math.PI); 
    ctx.fillStyle = 'red';
    ctx.fill();
    ctx.stroke();

}

export function clearGraph() {
    const canvas = document.getElementById("canvas"); 
    const ctx = canvas.getContext('2d');
    const canvasWidth = canvas.width;
    const canvasHeight = canvas.height;
    ctx.clearRect(0, 0, canvasWidth, canvasHeight);
    drawGraph();
}


drawGraph();

