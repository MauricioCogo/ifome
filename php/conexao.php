<?php

$servidor = 'localhost';
$banco      = 'bdifome';
$usuario  = 'root';
$senha    = '';

$cone = mysqli_connect($servidor, $usuario, $senha, $banco);

function response($data_res)
{
    header("Content-Type: application/json");
    echo json_encode($data_res);
}
?>

