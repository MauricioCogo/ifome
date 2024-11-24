<?php

require("conexao.php");

$u = json_decode(file_get_contents('php://input'), true);

$data['status'] = 'ERROR';

if ($u === null) {
    $data['message'] = 'Invalid JSON input';
    response($data);
    exit;
}

$nome = $u['nome'];
$senha = $u['senha'];

$sql = "SELECT * FROM usuario WHERE nome = '$nome' AND senha = '$senha'";
$result = $cone->query($sql);

if (mysqli_num_rows($result) > 0) {
	$data['status'] = 'SUCCESS';
    $data['usuario'] = mysqli_fetch_assoc($result);

} else {
	$data['message'] = 'Erro ao ler o usuário: ';
}


response($data);

?>

