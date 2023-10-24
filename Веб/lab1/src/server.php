<?php

$jsonInput = file_get_contents('php://input');
$data = json_decode($jsonInput, true);

if (!empty($data)) {
    if (isset($data['X']) && isset($data['Y']) && isset($data['R'])) {
        $x_d = $data['X'];
        $y_d = $data['Y'];
        $r_d = $data['R'];
        if (is_numeric($x_d) && is_numeric($y_d) && is_numeric($r_d)){
            $R = floatval($data['R']);
            $X = floatval($data['X']); 
            $Y = floatval($data['Y']); 

          if (in_array($X,[-3, -2, -1, 0, 1, 2, 3, 4, 5]) && $Y > -3 && $Y < 3 && $R > 2 && $R < 5){
            $time_of_start = microtime(true);

            $result = checkPointInArea($R, $X, $Y);

            $time_of_work = microtime(true) - $time_of_start; 

            $response = array(
                'R' => $R,
                'X' => $X,
                'Y' => $Y,
                'Result' => $result,
                'CurrentTime' => date('Y-m-d H:i:s'),
                'ScriptExecutionTime' => $time_of_work
            );

            header('Content-Type: application/json');
            echo json_encode($response);
        } else {
            echo 'Invalid data';
        }
    }else{
        echo  json_encode('Invalid data');
    }
    }else{
        echo  json_encode('Invalid data');
    }
}else{
    echo  json_encode('Invalid data');
}

function checkPointInArea($R, $X, $Y) {
    $sectorI = ($X >= 0) && ($Y >= 0) && ($X * $X + $Y * $Y <= $R * $R / 4); // Здесь исправлены опечатки
    $sectorIV = ($X <= 0) && ($Y >= 0) && (-2 * $X + $Y <= $R); // Здесь исправлены опечатки
    $sectorIII = ($X <= 0) && ($Y <= 0) && ($X >= -$R / 2) && ($Y >= -$R); // Здесь исправлены опечатки
    if ($sectorI || $sectorIV || $sectorIII) {
        return 'Hits';
    } else {
        return 'Miss';
    }
}
