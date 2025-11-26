SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
START TRANSACTION;

--
-- Banco de dados: `curie`
--

--
-- Estrutura para tabela `areas_carreira`
--
-- CORREÇÃO: PRIMARY KEY e AUTO_INCREMENT definidos na criação da tabela.
CREATE TABLE IF NOT EXISTS `areas_carreira` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `areas_conhecimento`
--
CREATE TABLE IF NOT EXISTS `areas_conhecimento` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `materias` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `assinaturas`
--
CREATE TABLE IF NOT EXISTS `assinaturas` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `cpf` VARCHAR(255) NOT NULL UNIQUE, -- Restrição UNIQUE adicionada aqui
  `data_compra` DATETIME(6) NOT NULL,
  `pagamento` ENUM('credito','debito','pix') NOT NULL,
  `tipo_assinatura_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `carreiras`
--
CREATE TABLE IF NOT EXISTS `carreiras` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `graduacao_id` INT(11) NOT NULL,
  `pos_graduacao_id` INT(11) NOT NULL,
  `profissao_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `comportamento`
--
CREATE TABLE IF NOT EXISTS `comportamento` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `aprendizagem` VARCHAR(255) NOT NULL,
  `caracteristicas` VARCHAR(255) NOT NULL,
  `descricao_estudo` VARCHAR(255) NOT NULL,
  `tipo_comportamento_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `equipes`
--
CREATE TABLE IF NOT EXISTS `equipes` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `assinatura_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `graduacoes`
--
CREATE TABLE IF NOT EXISTS `graduacoes` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `duracao` INT(11) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `area_carreira_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `membros_equipe`
--
CREATE TABLE IF NOT EXISTS `membros_equipe` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `equipe_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `metas`
--
CREATE TABLE IF NOT EXISTS `metas` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `fim` DATETIME(6) NOT NULL,
  `inicio` DATETIME(6) NOT NULL,
  `objetivo` VARCHAR(255) NOT NULL,
  `prioridade` ENUM('alta','baixa','media') NOT NULL,
  `status` ENUM('concluida','pendente') NOT NULL,
  `user_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `perfis`
--
CREATE TABLE IF NOT EXISTS `perfis` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `comportamento_id` INT(11) DEFAULT NULL,
  `forca_educacional_id` INT(11) DEFAULT NULL,
  `fraqueza_educacional_id` INT(11) DEFAULT NULL,
  `temperamento_id` INT(11) DEFAULT NULL,
  `user_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `pos_graduacoes`
--
CREATE TABLE IF NOT EXISTS `pos_graduacoes` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `duracao` INT(11) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `area_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `profissoes`
--
CREATE TABLE IF NOT EXISTS `profissoes` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `demanda` ENUM('alta','baixa','media') NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `salario` DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `roles`
--
CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL UNIQUE -- Restrição UNIQUE adicionada aqui
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `tarefas`
--
CREATE TABLE IF NOT EXISTS `tarefas` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `prazo` DATETIME(6) NOT NULL,
  `prioridade` ENUM('alta','baixa','media') NOT NULL,
  `status` ENUM('concluida','pendente') NOT NULL,
  `user_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `temperamento`
--
CREATE TABLE IF NOT EXISTS `temperamento` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `forca_aprendizado` VARCHAR(255) NOT NULL,
  `fraqueza_aprendizado` VARCHAR(255) NOT NULL,
  `tipo_temperamento_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `tipos_assinatura`
--
CREATE TABLE IF NOT EXISTS `tipos_assinatura` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `valor` DOUBLE NOT NULL,
  `user_id` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `tipo_comportamento`
--
CREATE TABLE IF NOT EXISTS `tipo_comportamento` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `tipo_temperamento`
--
CREATE TABLE IF NOT EXISTS `tipo_temperamento` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `users`
--
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `descricao` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE, -- Restrição UNIQUE adicionada aqui
  `nascimento` DATETIME(6) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `telefone` VARCHAR(255) NOT NULL,
  `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estrutura para tabela `users_roles`
--
-- Tabela de relacionamento: Chave primária composta definida aqui
CREATE TABLE IF NOT EXISTS `users_roles` (
  `user_id` INT(11) NOT NULL,
  `role_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Restrições (Foreign Keys) para tabelas despejadas
--

-- O bloco de FOREIGN KEYs deve ser mantido, pois define o relacionamento entre as tabelas.
-- Ele não conflita com a definição da PRIMARY KEY e pode ser executado a cada deploy (IF NOT EXISTS não é necessário aqui, pois se as tabelas existirem, o ALTER TABLE apenas adiciona a restrição).

ALTER TABLE `assinaturas`
  ADD CONSTRAINT `FK9ap168cau2cm9g5s4ho1ar4kk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKegt2a5axd25b4s0ivpvkdkpla` FOREIGN KEY (`tipo_assinatura_id`) REFERENCES `tipos_assinatura` (`id`);

ALTER TABLE `carreiras`
  ADD CONSTRAINT `FK3a2mm1d0rreo80145732mhab8` FOREIGN KEY (`profissao_id`) REFERENCES `profissoes` (`id`),
  ADD CONSTRAINT `FK4ylwsprbl1ndfty6ywxw1wpa3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK8p0gv0s2qruxmdjl3cma9j9tj` FOREIGN KEY (`pos_graduacao_id`) REFERENCES `pos_graduacoes` (`id`),
  ADD CONSTRAINT `FKbmqraqknxrbdudfkrcu9blre5` FOREIGN KEY (`graduacao_id`) REFERENCES `graduacoes` (`id`);

ALTER TABLE `comportamento`
  ADD CONSTRAINT `FK20kkpdrtmi3j5tny7tqobtsqr` FOREIGN KEY (`tipo_comportamento_id`) REFERENCES `tipo_comportamento` (`id`);

ALTER TABLE `equipes`
  ADD CONSTRAINT `FKm6rvb4qgkfoq8k3q0145enyb2` FOREIGN KEY (`assinatura_id`) REFERENCES `assinaturas` (`id`);

ALTER TABLE `graduacoes`
  ADD CONSTRAINT `FKbanuvos68n97vgu70gjj52yko` FOREIGN KEY (`area_carreira_id`) REFERENCES `areas_carreira` (`id`);

ALTER TABLE `membros_equipe`
  ADD CONSTRAINT `FK5lgghc8nbkqt6tev6v90ibjau` FOREIGN KEY (`equipe_id`) REFERENCES `equipes` (`id`),
  ADD CONSTRAINT `FK5m9i9n3ol5h2fy5bws9vp7x12` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `metas`
  ADD CONSTRAINT `FK4trg01df8ai6htmfd1vxydw87` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `perfis`
  ADD CONSTRAINT `FK44d9gja0ugvufbr5qmytj46aa` FOREIGN KEY (`forca_educacional_id`) REFERENCES `areas_conhecimento` (`id`),
  ADD CONSTRAINT `FK5paoj2nt07ti1kko042n06g8d` FOREIGN KEY (`fraqueza_educacional_id`) REFERENCES `areas_conhecimento` (`id`),
  ADD CONSTRAINT `FK99te8qrfirr8a8nr6cxv992ir` FOREIGN KEY (`comportamento_id`) REFERENCES `comportamento` (`id`),
  ADD CONSTRAINT `FK9rkqjge7s86u4j6758f71sih0` FOREIGN KEY (`temperamento_id`) REFERENCES `temperamento` (`id`),
  ADD CONSTRAINT `FKsv29wc7rgm4d7lanlrhy7vhs5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `pos_graduacoes`
  ADD CONSTRAINT `FKk758a7e6disqxn9oy9fm45hrp` FOREIGN KEY (`area_id`) REFERENCES `areas_carreira` (`id`);

ALTER TABLE `tarefas`
  ADD CONSTRAINT `FKl5y3bthkrl8o12q0ki57x1rxj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `temperamento`
  ADD CONSTRAINT `FKbnl35a2cc7gjys23r06vcle91` FOREIGN KEY (`tipo_temperamento_id`) REFERENCES `tipo_temperamento` (`id`);

ALTER TABLE `tipos_assinatura`
  ADD CONSTRAINT `FK5jun4yvakywd4kjgpmh1u0qef` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

COMMIT;