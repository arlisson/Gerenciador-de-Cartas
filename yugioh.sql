-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 10-Nov-2024 às 19:16
-- Versão do servidor: 10.4.27-MariaDB
-- versão do PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `yugioh`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cartas`
--

CREATE TABLE `cartas` (
  `id` int(11) NOT NULL,
  `nome_carta` varchar(255) DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `colecao` varchar(255) DEFAULT NULL,
  `ano_compra` varchar(255) DEFAULT NULL,
  `valor_pago` float DEFAULT NULL,
  `ano_venda` varchar(255) DEFAULT 'Não vendida',
  `valor_venda` float DEFAULT NULL,
  `raridade` varchar(255) DEFAULT 'NORMAL',
  `quantidade` int(11) DEFAULT 1,
  `qualidade` varchar(255) DEFAULT 'NORMAL (M)',
  `imagem` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `qualidade` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `qualidade` (`id`, `descricao`) VALUES
(1, 'NOVA (M)'),
(2, 'QUASE NOVA (NM)'),
(3, 'LIGEIRAMENTE JOGADA (SP)'),
(4, 'MODERADAMENTE JOGADA (MP)'),
(5, 'JOGADA DEMAIS(HP)'),
(6, 'DANIFICADA (DM)');

CREATE TABLE `raridade` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `raridade` (`id`, `descricao`) VALUES
(1, 'NORMAL'),
(2, 'RARA'),
(3, 'SUPER RARA'),
(4, 'ULTRA RARA'),
(5, 'ULTIMATE RARA'),
(6, 'HOLOGRAPHIC RARE'),
(7, 'SECRETA RARA');

ALTER TABLE `cartas`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `qualidade`
--
ALTER TABLE `qualidade`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `raridade`
--
ALTER TABLE `raridade`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cartas`
--
ALTER TABLE `cartas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=409;

--
-- AUTO_INCREMENT de tabela `qualidade`
--
ALTER TABLE `qualidade`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `raridade`
--
ALTER TABLE `raridade`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
