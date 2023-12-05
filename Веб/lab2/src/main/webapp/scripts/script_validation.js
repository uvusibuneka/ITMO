function checkAfterPointLength(num){
   const regex = /^\d+\.\d{15}/;
    return !regex.test(num.toString());
}

function isValidNum(N){
    return N !== '' && !isNaN(N) && checkAfterPointLength(N);
}

export function isXValid(X){
    return isValidNum(X);// && X >= -3 && X <= 5;
}
export function isYValid(Y){
   return isValidNum(Y) && Y > -5 && Y < 3;
}
export function isRValid(R){
    return isValidNum(R);// && R > 2 && R < 5;
}
export function validateData(R, X, Y) {
    return isRValid(R) && isXValid(X) && isYValid(Y);
}
