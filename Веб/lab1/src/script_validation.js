
function checkAfterPointLength(num){
   const regex = /^\d+\.\d{15}/;
    return !regex.test(num.toString());
}

export function validateData(R, X, Y) {
    console.log(X);
    console.log(Y);
    console.log(R);
    const isRValid = !isNaN(R) && R > 2 && R < 5 && checkAfterPointLength(R); 
    const isXValid = !isNaN(X) && X >= -3 && X <= 5 && checkAfterPointLength(X); 
    const isYValid = !isNaN(Y) && Y > -3 && Y < 3 && checkAfterPointLength(Y); 
    console.log(isRValid);
    console.log(isYValid);
    console.log(isXValid);

if (!isRValid) {
    document.getElementById('errorR').style.display = 'block';
} else {
    document.getElementById('errorR').style.display = 'none'; 
}

if (!isXValid) {
    document.getElementById('errorX').style.display = 'block'; 
} else {
    document.getElementById('errorX').style.display = 'none'; 
}
if (!isYValid) {
    document.getElementById('errorY').style.display = 'block'; 
} else {
    document.getElementById('errorY').style.display = 'none'; 
}
    return isRValid && isXValid && isYValid;
}
