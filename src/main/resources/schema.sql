-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 26/11/2025 às 13:37
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `curie`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `areas_carreira`
--

CREATE TABLE `areas_carreira` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `areas_conhecimento`
--

CREATE TABLE `areas_conhecimento` (
  `id` int(11) NOT NULL,
  `materias` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `assinaturas`
--

CREATE TABLE `assinaturas` (
  `id` int(11) NOT NULL,
  `cpf` varchar(255) NOT NULL,
  `data_compra` datetime(6) NOT NULL,
  `pagamento` enum('credito','debito','pix') NOT NULL,
  `tipo_assinatura_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `carreiras`
--

CREATE TABLE `carreiras` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `graduacao_id` int(11) NOT NULL,
  `pos_graduacao_id` int(11) NOT NULL,
  `profissao_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `comportamento`
--

CREATE TABLE `comportamento` (
  `id` int(11) NOT NULL,
  `aprendizagem` varchar(255) NOT NULL,
  `caracteristicas` varchar(255) NOT NULL,
  `descricao_estudo` varchar(255) NOT NULL,
  `tipo_comportamento_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `equipes`
--

CREATE TABLE `equipes` (
  `id` int(11) NOT NULL,
  `assinatura_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `graduacoes`
--

CREATE TABLE `graduacoes` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `duracao` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `area_carreira_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `membros_equipe`
--

CREATE TABLE `membros_equipe` (
  `id` int(11) NOT NULL,
  `equipe_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `metas`
--

CREATE TABLE `metas` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `fim` datetime(6) NOT NULL,
  `inicio` datetime(6) NOT NULL,
  `objetivo` varchar(255) NOT NULL,
  `prioridade` enum('alta','baixa','media') NOT NULL,
  `status` enum('concluida','pendente') NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `perfis`
--

CREATE TABLE `perfis` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `comportamento_id` int(11) DEFAULT NULL,
  `forca_educacional_id` int(11) DEFAULT NULL,
  `fraqueza_educacional_id` int(11) DEFAULT NULL,
  `temperamento_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `pos_graduacoes`
--

CREATE TABLE `pos_graduacoes` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `duracao` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `area_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `profissoes`
--

CREATE TABLE `profissoes` (
  `id` int(11) NOT NULL,
  `demanda` enum('alta','baixa','media') NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `salario` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `roles`
--

CREATE TABLE `roles` (
  `role_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tarefas`
--

CREATE TABLE `tarefas` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `prazo` datetime(6) NOT NULL,
  `prioridade` enum('alta','baixa','media') NOT NULL,
  `status` enum('concluida','pendente') NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `temperamento`
--

CREATE TABLE `temperamento` (
  `id` int(11) NOT NULL,
  `forca_aprendizado` varchar(255) NOT NULL,
  `fraqueza_aprendizado` varchar(255) NOT NULL,
  `tipo_temperamento_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tipos_assinatura`
--

CREATE TABLE `tipos_assinatura` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `valor` double NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tipo_comportamento`
--

CREATE TABLE `tipo_comportamento` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tipo_temperamento`
--

CREATE TABLE `tipo_temperamento` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  `descricao` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nascimento` datetime(6) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL DEFAULT current_timestamp(6) ON UPDATE current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `areas_carreira`
--
ALTER TABLE `areas_carreira`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `areas_conhecimento`
--
ALTER TABLE `areas_conhecimento`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `assinaturas`
--
ALTER TABLE `assinaturas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKhy31x68r2njw1p1jgk5llro8c` (`cpf`),
  ADD KEY `FKegt2a5axd25b4s0ivpvkdkpla` (`tipo_assinatura_id`),
  ADD KEY `FK9ap168cau2cm9g5s4ho1ar4kk` (`user_id`);

--
-- Índices de tabela `carreiras`
--
ALTER TABLE `carreiras`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbmqraqknxrbdudfkrcu9blre5` (`graduacao_id`),
  ADD KEY `FK8p0gv0s2qruxmdjl3cma9j9tj` (`pos_graduacao_id`),
  ADD KEY `FK3a2mm1d0rreo80145732mhab8` (`profissao_id`),
  ADD KEY `FK4ylwsprbl1ndfty6ywxw1wpa3` (`user_id`);

--
-- Índices de tabela `comportamento`
--
ALTER TABLE `comportamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK20kkpdrtmi3j5tny7tqobtsqr` (`tipo_comportamento_id`);

--
-- Índices de tabela `equipes`
--
ALTER TABLE `equipes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm6rvb4qgkfoq8k3q0145enyb2` (`assinatura_id`);

--
-- Índices de tabela `graduacoes`
--
ALTER TABLE `graduacoes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbanuvos68n97vgu70gjj52yko` (`area_carreira_id`);

--
-- Índices de tabela `membros_equipe`
--
ALTER TABLE `membros_equipe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5lgghc8nbkqt6tev6v90ibjau` (`equipe_id`),
  ADD KEY `FK5m9i9n3ol5h2fy5bws9vp7x12` (`user_id`);

--
-- Índices de tabela `metas`
--
ALTER TABLE `metas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4trg01df8ai6htmfd1vxydw87` (`user_id`);

--
-- Índices de tabela `perfis`
--
ALTER TABLE `perfis`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK99te8qrfirr8a8nr6cxv992ir` (`comportamento_id`),
  ADD KEY `FK44d9gja0ugvufbr5qmytj46aa` (`forca_educacional_id`),
  ADD KEY `FK5paoj2nt07ti1kko042n06g8d` (`fraqueza_educacional_id`),
  ADD KEY `FK9rkqjge7s86u4j6758f71sih0` (`temperamento_id`),
  ADD KEY `FKsv29wc7rgm4d7lanlrhy7vhs5` (`user_id`);

--
-- Índices de tabela `pos_graduacoes`
--
ALTER TABLE `pos_graduacoes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKk758a7e6disqxn9oy9fm45hrp` (`area_id`);

--
-- Índices de tabela `profissoes`
--
ALTER TABLE `profissoes`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Índices de tabela `tarefas`
--
ALTER TABLE `tarefas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl5y3bthkrl8o12q0ki57x1rxj` (`user_id`);

--
-- Índices de tabela `temperamento`
--
ALTER TABLE `temperamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbnl35a2cc7gjys23r06vcle91` (`tipo_temperamento_id`);

--
-- Índices de tabela `tipos_assinatura`
--
ALTER TABLE `tipos_assinatura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5jun4yvakywd4kjgpmh1u0qef` (`user_id`);

--
-- Índices de tabela `tipo_comportamento`
--
ALTER TABLE `tipo_comportamento`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `tipo_temperamento`
--
ALTER TABLE `tipo_temperamento`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Índices de tabela `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `areas_carreira`
--
ALTER TABLE `areas_carreira`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `areas_conhecimento`
--
ALTER TABLE `areas_conhecimento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `assinaturas`
--
ALTER TABLE `assinaturas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `carreiras`
--
ALTER TABLE `carreiras`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `comportamento`
--
ALTER TABLE `comportamento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `equipes`
--
ALTER TABLE `equipes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `graduacoes`
--
ALTER TABLE `graduacoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `membros_equipe`
--
ALTER TABLE `membros_equipe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `metas`
--
ALTER TABLE `metas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `perfis`
--
ALTER TABLE `perfis`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `pos_graduacoes`
--
ALTER TABLE `pos_graduacoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `profissoes`
--
ALTER TABLE `profissoes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tarefas`
--
ALTER TABLE `tarefas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `temperamento`
--
ALTER TABLE `temperamento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tipos_assinatura`
--
ALTER TABLE `tipos_assinatura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tipo_comportamento`
--
ALTER TABLE `tipo_comportamento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tipo_temperamento`
--
ALTER TABLE `tipo_temperamento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `assinaturas`
--
ALTER TABLE `assinaturas`
  ADD CONSTRAINT `FK9ap168cau2cm9g5s4ho1ar4kk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKegt2a5axd25b4s0ivpvkdkpla` FOREIGN KEY (`tipo_assinatura_id`) REFERENCES `tipos_assinatura` (`id`);

--
-- Restrições para tabelas `carreiras`
--
ALTER TABLE `carreiras`
  ADD CONSTRAINT `FK3a2mm1d0rreo80145732mhab8` FOREIGN KEY (`profissao_id`) REFERENCES `profissoes` (`id`),
  ADD CONSTRAINT `FK4ylwsprbl1ndfty6ywxw1wpa3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK8p0gv0s2qruxmdjl3cma9j9tj` FOREIGN KEY (`pos_graduacao_id`) REFERENCES `pos_graduacoes` (`id`),
  ADD CONSTRAINT `FKbmqraqknxrbdudfkrcu9blre5` FOREIGN KEY (`graduacao_id`) REFERENCES `graduacoes` (`id`);

--
-- Restrições para tabelas `comportamento`
--
ALTER TABLE `comportamento`
  ADD CONSTRAINT `FK20kkpdrtmi3j5tny7tqobtsqr` FOREIGN KEY (`tipo_comportamento_id`) REFERENCES `tipo_comportamento` (`id`);

--
-- Restrições para tabelas `equipes`
--
ALTER TABLE `equipes`
  ADD CONSTRAINT `FKm6rvb4qgkfoq8k3q0145enyb2` FOREIGN KEY (`assinatura_id`) REFERENCES `assinaturas` (`id`);

--
-- Restrições para tabelas `graduacoes`
--
ALTER TABLE `graduacoes`
  ADD CONSTRAINT `FKbanuvos68n97vgu70gjj52yko` FOREIGN KEY (`area_carreira_id`) REFERENCES `areas_carreira` (`id`);

--
-- Restrições para tabelas `membros_equipe`
--
ALTER TABLE `membros_equipe`
  ADD CONSTRAINT `FK5lgghc8nbkqt6tev6v90ibjau` FOREIGN KEY (`equipe_id`) REFERENCES `equipes` (`id`),
  ADD CONSTRAINT `FK5m9i9n3ol5h2fy5bws9vp7x12` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Restrições para tabelas `metas`
--
ALTER TABLE `metas`
  ADD CONSTRAINT `FK4trg01df8ai6htmfd1vxydw87` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Restrições para tabelas `perfis`
--
ALTER TABLE `perfis`
  ADD CONSTRAINT `FK44d9gja0ugvufbr5qmytj46aa` FOREIGN KEY (`forca_educacional_id`) REFERENCES `areas_conhecimento` (`id`),
  ADD CONSTRAINT `FK5paoj2nt07ti1kko042n06g8d` FOREIGN KEY (`fraqueza_educacional_id`) REFERENCES `areas_conhecimento` (`id`),
  ADD CONSTRAINT `FK99te8qrfirr8a8nr6cxv992ir` FOREIGN KEY (`comportamento_id`) REFERENCES `comportamento` (`id`),
  ADD CONSTRAINT `FK9rkqjge7s86u4j6758f71sih0` FOREIGN KEY (`temperamento_id`) REFERENCES `temperamento` (`id`),
  ADD CONSTRAINT `FKsv29wc7rgm4d7lanlrhy7vhs5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Restrições para tabelas `pos_graduacoes`
--
ALTER TABLE `pos_graduacoes`
  ADD CONSTRAINT `FKk758a7e6disqxn9oy9fm45hrp` FOREIGN KEY (`area_id`) REFERENCES `areas_carreira` (`id`);

--
-- Restrições para tabelas `tarefas`
--
ALTER TABLE `tarefas`
  ADD CONSTRAINT `FKl5y3bthkrl8o12q0ki57x1rxj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Restrições para tabelas `temperamento`
--
ALTER TABLE `temperamento`
  ADD CONSTRAINT `FKbnl35a2cc7gjys23r06vcle91` FOREIGN KEY (`tipo_temperamento_id`) REFERENCES `tipo_temperamento` (`id`);

--
-- Restrições para tabelas `tipos_assinatura`
--
ALTER TABLE `tipos_assinatura`
  ADD CONSTRAINT `FK5jun4yvakywd4kjgpmh1u0qef` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Restrições para tabelas `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;