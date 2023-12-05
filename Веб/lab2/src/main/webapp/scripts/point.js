function addPoint(R, X, Y) {
    const canvas = document.getElementById("canvas");
    console.log("hered point");
    const ctx = canvas.getContext('2d');
    const Rstandard = (Math.min(canvas.height, canvas.width) / 2) * 0.9
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