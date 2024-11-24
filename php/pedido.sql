-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 24/11/2024 às 23:27
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `bdifome`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedido`
--

CREATE TABLE `pedido` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `id_usuario` bigint(20) UNSIGNED NOT NULL,
  `pizza` tinyint(1) NOT NULL,
  `tamanho` varchar(255) DEFAULT NULL,
  `sabor` varchar(255) DEFAULT NULL,
  `bebida` tinyint(1) NOT NULL,
  `desc_bebida` varchar(255) DEFAULT NULL,
  `tele` tinyint(1) NOT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `data` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pedido`
--

INSERT INTO `pedido` (`id`, `id_usuario`, `pizza`, `tamanho`, `sabor`, `bebida`, `desc_bebida`, `tele`, `endereco`, `data`) VALUES
(12, 2, 1, 'grande', 'frango', 0, '', 0, '', '2024-11-24 22:25:23'),
(13, 2, 1, 'pequana', 'calabrssa', 0, '', 1, 'casa', '2024-11-24 22:25:34'),
(14, 2, 1, 'media', 'queijo', 0, '', 0, '', '2024-11-24 22:25:42'),
(15, 2, 0, '', '', 1, 'coca', 1, 'casa', '2024-11-24 22:26:00'),
(16, 2, 0, '', '', 1, 'coca cola', 0, '', '2024-11-24 22:26:05');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `pedido_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `pedido_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
