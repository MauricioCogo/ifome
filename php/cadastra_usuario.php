<?php
require("conexao.php");

$u = json_decode(file_get_contents('php://input'), true);

$stmt = $cone->prepare("INSERT INTO usuario (nome, senha, endereço, fone, cidade) VALUES (?,?,?,?,?)");
$stmt->bind_param("sssss", $u['nome'], $u['senha'], $u['endereco'], $u['fone'], $u['cidade']);

$data['status'] = 'ERROR';

if ($stmt->execute()) {
    $data['status'] = 'SUCCESS';
} else {
    $data['message'] = 'Erro ao criar o usuário: ' . $stmt->error;
}

response($data);

?>

