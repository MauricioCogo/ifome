<?php

include("conexao.php");

$s = json_decode(file_get_contents('php://input'), true);

$data['status'] = 'ERROR';

$id = $s['id_usuario'];
$pizza = $s['pizza'];
$tamanho = $s['tamanho'];
$sabor = $s['sabor'];
$bebida = $s['bebida'];
$desc_bebida = $s['desc_bebida'];
$tele = $s['tele'];
$endereco = $s['endereco'];

$stmt = $cone->prepare("
    INSERT INTO pedido (id_usuario, pizza, tamanho, sabor, bebida, desc_bebida, tele, endereco) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
");

$stmt->bind_param("ssssssss", $s['id_usuario'], $s['pizza'], $s['tamanho'], $s['sabor'], $s['bebida'], $s['desc_bebida'], $s['tele'], $s['endereco']);

if ($stmt->execute()) {
    $data['status'] = 'SUCCESS';
} else {
    $data['message'] = 'Erro ao criar o pedido: ' . $stmt->error;
}

response($data);


?>