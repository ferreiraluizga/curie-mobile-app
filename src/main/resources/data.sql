-- Roles
INSERT IGNORE INTO roles (role_id, name) VALUES (1, 'admin');
INSERT IGNORE INTO roles (role_id, name) VALUES (2, 'basic');

-- Tipos de Comportamento
INSERT IGNORE INTO `tipo_comportamento` (`id`, `descricao`, `nome`) VALUES
(1, 'Você se destaca pela energia, iniciativa e coragem para decidir. Gosta de desafios e de estar no controle das situações. É direto, determinado e orientado pelos resultados e consequências.', 'Proativo'),
(2, 'Você se comunica com facilidade, inspira confiança e gosta de estar entre pessoas. É criativo, empático e aprende bem conversando, trocando experiências ou explicando para os outros.', 'Comunicativo'),
(3, 'Você é um bom ouvinte, paciente e confiável. Mantém a compostura mesmo em situações de pressão. Gosta de rotina, previsibilidade e evita mudanças bruscas.', 'Estável'),
(4, 'Você valoriza organização, lógica e qualidade. Observa detalhes, segue planos e regras. Prefere estudar com tempo suficiente para entender todos os detalhes.', 'Analítico'),
(5, 'Você é uma pessoa dinâmica, que toma iniciativa e inspira os outros por meio da comunicação. Gosta de liderar grupos, expressar ideias e gerar entusiasmo nas pessoas ao redor. É enérgico, aprende melhor quando há interação social e desafios concretos', 'Proativo-Comunicativo'),
(6, 'Você combina iniciativa e foco com serenidade e paciência. Age de forma firme, mas raramente impulsiva. Prefere resolver as coisas de maneira prática, sem perder o controle emocional. É bom em situações de pressão porque mantém a calma sem deixar de agir.', 'Proativo-Estável'),
(7, 'Você é alguém que une ação e planejamento. Gosta de resultados concretos, mas valoriza a preparação antes de agir. Sua força está em transformar planos em realidade. Costuma liderar com base em lógica e estratégia, e tende a ser exigente consigo mesmo.', 'Proativo-Analítico'),
(8, 'Você é empático e acessível, sabendo equilibrar emoção e calma. Cria harmonia nos grupos e tem facilidade em ouvir e acolher. Pode evitar conflitos excessivamente.', 'Comunicativo-Estável'),
(9, 'Você combina expressão verbal com raciocínio lógico. Gosta de expor ideias de maneira organizada e tem talento para ensinar e explicar. Sua força está em transformar conceitos complexos em algo acessível aos outros.', 'Comunicativo-Analítico'),
(10, 'Você é calmo, organizado e minucioso. Prefere compreender cada parte do processo antes de agir. Trabalha bem sob rotina e preza por consistência e qualidade. Pode demorar um pouco mais, mas entrega com precisão e constância.', 'Estável-Analítico');

-- Tipos de Temperamento
INSERT IGNORE INTO `tipo_temperamento` (`id`, `nome`, `descricao`) VALUES
(1, 'Colérico', 'O colérico é determinado, direto e voltado para resultados. Gosta de desafios e tende a assumir o comando nas situações. É prático, confiante e movido por objetivos concretos. Por outro lado, pode demonstrar impaciência, rigidez ou dificuldade em lidar com a lentidão dos outros. É um perfil de energia e liderança, que prefere agir a esperar.'),
(2, 'Sanguíneo', 'O sanguíneo é comunicativo, entusiasmado e sociável. Costuma enxergar o lado positivo das coisas e tem facilidade em motivar quem está ao redor. É criativo, espontâneo e adaptável, mas às vezes dispersa ou se deixa levar por emoções momentâneas. Valoriza conexões humanas e ambientes leves, onde possa se expressar livremente.'),
(3, 'Fleumático', 'O fleumático é calmo, estável e observador. Prefere agir com cautela e evita conflitos sempre que possível. Tem grande capacidade de ouvir, adaptar-se e manter a harmonia. Pode ser reservado e demorar a reagir em situações novas, mas transmite equilíbrio e constância. É o tipo que sustenta a paz e o bom senso em meio às mudanças.'),
(4, 'Melancólico', 'O melancólico é analítico, sensível e perfeccionista. Busca compreender as coisas com profundidade e se guia por valores e princípios firmes. É dedicado, detalhista e responsável, mas pode ser exigente consigo mesmo e ter dificuldade em lidar com erros. Sua força está na reflexão e na busca pela excelência.'),
(5, 'Colérico–Sanguíneo', 'O Colérico–Sanguíneo combina iniciativa e energia com capacidade de envolvimento social. É assertivo e comunicativo, capaz de liderar projetos enquanto motiva e engaja pessoas ao redor. Tende a ser persuasivo, criativo na busca de soluções e orientado a resultados visíveis. Por outro lado, pode oscilar entre agir impulsivamente e buscar aprovação social; precisa cuidar para não priorizar velocidade ou popularidade em detrimento da análise cuidadosa.'),
(6, 'Colérico–Fleumático', 'O Colérico–Fleumático une determinação e estabilidade. Age com propósito e mantém a calma sob pressão, equilibrando ação com controle emocional. É eficaz na execução de tarefas e consistente na manutenção de rotinas, sendo bom em conduzir equipes sem provocar tensões. Pode, porém, alternar entre urgência por resultados e resistência a mudanças rápidas; às vezes tende a adiar decisões impulsivas em favor de cautela excessiva.'),
(7, 'Colérico–Melancólico', 'O Colérico–Melancólico combina foco por resultados com rigor analítico. Planeja com precisão e executa com determinação, valorizando tanto a eficácia quanto a qualidade do trabalho. É estratégico, exigente e orientado a metas bem fundamentadas. Risco: pode tornar-se rígido e autocrítico, cobrando demais a si e aos outros; precisa equilibrar a busca pela perfeição com flexibilidade prática.'),
(8, 'Sanguíneo–Fleumático', 'O Sanguíneo–Fleumático mistura sociabilidade e estabilidade emocional. Tem facilidade para criar conexões, ao mesmo tempo que transmite segurança e serenidade. Costuma ser acolhedor, confiável e cooperativo, favorecendo ambientes harmoniosos e colaborativos. Pode, no entanto, protelar decisões que impliquem conflito ou mudança, preferindo conservar a paz mesmo quando ações firmes seriam necessárias.'),
(9, 'Sanguíneo–Melancólico', 'O perfil Sanguíneo–Melancólico reúne sensibilidade e expressividade criativa. Une empatia e habilidade de comunicação com gosto por reflexão e profundidade. Produz ideias com conteúdo emocional bem elaborado e costuma envolver pessoas com argumentos bem pensados. Pode oscilar entre entusiasmo social e introspecção intensa, correndo o risco de sentir insegurança ao expor trabalhos ainda em desenvolvimento.'),
(10, 'Fleumático–Melancólico', 'O perfil Fleumático–Melancólico é ponderado, metódico e confiável. Prioriza rotina, organização e reflexão cuidadosa; age com paciência e busca consistência nos resultados. Tem boa capacidade de análise calma e de seguir processos até a conclusão. Pode, contudo, demorar a tomar iniciativas e apresentar resistência a mudanças rápidas; tende a preferir segurança e profundidade antes de agir.');

-- Areas de Conhecimento
INSERT IGNORE INTO `areas_conhecimento` (`id`, `materias`, `nome`) VALUES
(1, 'História, Geografia, Filosofia, Sociologia', 'Ciências Humanas'),
(2, 'Matemática, Física, Química', 'Matemática e suas Tecnologias'),
(3, 'Biologia, Física, Química', 'Ciências da Natureza'),
(4, 'Língua Portuguesa, Literatura, Artes, Inglês, Espanhol, Educação Física', 'Linguagens e Códigos');

/* ===========================================================
   1. ÁREAS DE CARREIRA
   =========================================================== */
INSERT IGNORE INTO areas_carreira (id, nome, descricao) VALUES
(1, 'Exatas', 'Foco em Matemática, Lógica e Aplicações Estruturais.'),
(2, 'Tecnologia', 'Foco em desenvolvimento de software, dados, redes e inovação digital.'),
(3, 'Engenharia', 'Foco no projeto, construção e otimização de sistemas e infraestrutura.'),
(4, 'Gestão', 'Foco na administração de equipes, recursos e processos para atingir metas.'),
(5, 'Administração', 'Estudo e aplicação de princípios de organização, planejamento e controle empresarial.'),
(6, 'Contabilidade', 'Foco em finanças, impostos, auditoria e registro de transações econômicas.'),
(7, 'Artes', 'Foco na expressão criativa, estética e produção cultural.'),
(8, 'Design', 'Foco na solução de problemas visuais, experiência do usuário e criação estética funcional.'),
(9, 'Comunicação', 'Foco em mídia, jornalismo, relações públicas e produção de conteúdo.'),
(10, 'Criativas', 'Área ampla focada em inovação, tendências e produção de conteúdo original (ex: Moda, Cinema).'),
(11, 'Publicidade', 'Foco na criação de campanhas, estratégias de marketing e persuasão para marcas.'),
(12, 'Moda', 'Foco em tendências, design de vestuário, gestão de marcas e produção de moda.'),
(13, 'Arquitetura', 'Foco no projeto e construção de espaços, edificações e planejamento urbano.'),
(14, 'Cinema', 'Foco na produção, direção e roteiro de obras audiovisuais.'),
(15, 'Humanas', 'Foco em sociedades, cultura, política, história e relações sociais.'),
(16, 'Educação', 'Foco em ensino, metodologias de aprendizagem, docência e gestão escolar.'),
(17, 'Psicologia', 'Foco no estudo do comportamento, mente humana e saúde mental.'),
(18, 'Social', 'Foco em assistência, defesa de direitos e intervenção em questões comunitárias (ex: Serviço Social).'),
(19, 'Ciências', 'Foco em Biologia, Física, Química e pesquisa laboratorial.'),
(20, 'Pesquisa', 'Foco na investigação científica, produção acadêmica e análise aprofundada de dados.'),
(21, 'Filosofia', 'Foco na reflexão fundamental sobre a existência, conhecimento e valores.');


/* ===========================================================
   2. GRADUAÇÕES (Atual + Complementos)
   =========================================================== */
INSERT IGNORE INTO graduacoes (id, nome, descricao, duracao, area_carreira_id) VALUES
-- Área 1: Exatas
(1001, 'Matemática', 'Estudo aprofundado de teoria matemática, lógica, álgebra e aplicações.', 4, 1),

-- Área 2: Tecnologia
(101, 'Engenharia de Software', 'Foco no desenvolvimento e manutenção de sistemas de software complexos.', 5, 2),
(102, 'Ciência de Dados', 'Análise de grandes volumes de dados para extrair insights e tomar decisões estratégicas.', 4, 2),

-- Área 3: Engenharia
(105, 'Engenharia Civil', 'Planejamento, projeto e execução de obras como edifícios, pontes e estradas.', 5, 3),

-- Área 4: Gestão
(304, 'Recursos Humanos', 'Gestão de pessoas, desenvolvimento de talentos e relações de trabalho.', 4, 4),

-- Área 5: Administração
(103, 'Administração de Empresas', 'Estudo da gestão de recursos, pessoas e processos em organizações.', 4, 5),

-- Área 6: Contabilidade
(104, 'Ciências Contábeis', 'Registro, análise e interpretação das transações financeiras e patrimoniais de uma empresa.', 4, 6),

-- Área 7: Artes
(1003, 'Artes Visuais', 'Formação em criação artística, expressão estética e produção cultural.', 4, 7),

-- Área 8: Design
(201, 'Design Gráfico', 'Criação de projetos visuais para comunicação de ideias e produtos.', 4, 8),

-- Área 9: Comunicação
(1004, 'Jornalismo', 'Produção de notícias, reportagens e conteúdos informativos.', 4, 9),

-- Área 10: Criativas
(1005, 'Design de Moda', 'Processos criativos aplicados ao vestuário e tendências.', 4, 10),

-- Área 11: Publicidade
(202, 'Publicidade e Propaganda', 'Desenvolvimento de campanhas e estratégias de comunicação e marketing.', 4, 11),

-- Área 12: Moda
(1006, 'Produção de Moda', 'Criação de coleções, styling, gestão e produção de moda.', 4, 12),

-- Área 13: Arquitetura
(204, 'Arquitetura e Urbanismo', 'Planejamento e projeto de espaços físicos e de cidades.', 5, 13),

-- Área 14: Cinema
(203, 'Cinema e Audiovisual', 'Estudo e prática da produção de filmes, vídeos e outras mídias visuais.', 4, 14),

-- Área 15: Humanas
(404, 'História', 'Estudo do passado humano e das sociedades ao longo do tempo.', 4, 15),

-- Área 16: Educação
(302, 'Pedagogia', 'Foco em métodos de ensino, gestão escolar e processos de aprendizagem.', 4, 16),

-- Área 17: Psicologia
(301, 'Psicologia', 'Estudo do comportamento humano e dos processos mentais.', 5, 17),

-- Área 18: Social
(303, 'Serviço Social', 'Atuação na defesa dos direitos sociais e na formulação de políticas públicas.', 4, 18),

-- Área 19: Ciências
(401, 'Biologia', 'Estudo dos seres vivos, da célula aos ecossistemas.', 4, 19),
(402, 'Física', 'Estudo das leis fundamentais da natureza e suas aplicações.', 4, 19),

-- Área 20: Pesquisa
(1008, 'Metodologia Científica', 'Formação voltada a métodos de pesquisa e investigação científica.', 4, 20),

-- Área 21: Filosofia
(403, 'Filosofia', 'Estudo das questões fundamentais sobre a existência, o conhecimento, os valores, a razão e a mente.', 4, 21);


/* ===========================================================
   3. PÓS-GRADUAÇÕES (1 por área)
   =========================================================== */
INSERT IGNORE INTO pos_graduacoes (id, nome, descricao, duracao, area_id) VALUES
-- 1. Exatas
(5001, 'Pós em Matemática Aplicada', 'Aplicações avançadas de matemática em ciência, indústria e tecnologia.', 2, 1),

-- 2. Tecnologia
(5002, 'Pós em Desenvolvimento de Sistemas', 'Engenharia avançada de software, arquitetura e cloud.', 2, 2),

-- 3. Engenharia
(5003, 'Pós em Gestão de Projetos de Engenharia', 'Planejamento e execução de obras e sistemas.', 2, 3),

-- 4. Gestão
(5004, 'MBA em Gestão Estratégica', 'Liderança e tomada de decisão organizacional.', 1, 4),

-- 5. Administração
(5005, 'MBA em Administração Estratégica', 'Gestão avançada de empresas e processos.', 1, 5),

-- 6. Contabilidade
(5006, 'Pós em Auditoria e Controladoria', 'Controle financeiro, compliance e auditoria.', 1, 6),

-- 7. Artes
(5007, 'Pós em Artes Contemporâneas', 'Pesquisa, curadoria e produção artística avançada.', 1, 7),

-- 8. Design
(5008, 'Pós em UX/UI Design', 'Pesquisa, prototipação e usabilidade de interfaces digitais.', 1, 8),

-- 9. Comunicação
(5009, 'Pós em Comunicação Digital', 'Estratégias de mídia, conteúdo e jornalismo digital.', 1, 9),

-- 10. Criativas
(5010, 'Pós em Produção Criativa', 'Indústrias criativas, inovação e branding.', 1, 10),

-- 11. Publicidade
(5011, 'Pós em Marketing Digital e Publicidade', 'Estratégias de marketing e campanhas publicitárias.', 1, 11),

-- 12. Moda
(5012, 'Pós em Gestão de Moda', 'Mercado fashion, branding e produção de coleções.', 1, 12),

-- 13. Arquitetura
(5013, 'Pós em Projeto Arquitetônico Avançado', 'Urbanismo, paisagismo e modelagem arquitetônica.', 2, 13),

-- 14. Cinema
(5014, 'Pós em Direção Cinematográfica', 'Direção, roteiro e produção audiovisual avançada.', 1, 14),

-- 15. Humanas
(5015, 'Pós em Ciências Humanas e Políticas Públicas', 'Sociedade, políticas e análise crítica.', 1, 15),

-- 16. Educação
(5016, 'Pós em Psicopedagogia', 'Ensino, aprendizagem e educação inclusiva.', 1, 16),

-- 17. Psicologia
(5017, 'Pós em Psicologia Clínica', 'Psicoterapia, psicopatologia e prática clínica.', 2, 17),

-- 18. Social
(5018, 'Pós em Políticas Públicas e Assistência Social', 'Gestão e intervenção em políticas sociais.', 1, 18),

-- 19. Ciências
(5019, 'Pós em Ciências Biológicas e Pesquisa Laboratorial', 'Pesquisa aplicada, análises e laboratório.', 2, 19),

-- 20. Pesquisa
(5020, 'Pós em Métodos de Pesquisa Científica', 'Estatística, métodos e investigação científica.', 1, 20),

-- 21. Filosofia
(5021, 'Pós em Filosofia Aplicada', 'Ética, lógica, epistemologia e pensamento crítico.', 1, 21);


-- 4. Profissões (Demanda: ALTA, MEDIA, BAIXA - Assumindo ENUM no DB)
INSERT IGNORE INTO profissoes (id, nome, descricao, salario, demanda) VALUES
-- Área 2: Tecnologia
(10001, 'Desenvolvedor Full Stack', 'Criação e manutenção de aplicações web completas (front-end e back-end).', 5500.00, 'ALTA'),
(10002, 'Engenheiro de Dados', 'Construção e otimização de pipelines de dados (ETL).', 6000.00, 'ALTA'),
-- Área 4: Gestão
(10003, 'Gerente de Projetos (TI)', 'Liderança e planejamento de projetos de desenvolvimento de software.', 7000.00, 'ALTA'),
(10004, 'Analista de RH (Treinamento e Desenvolvimento)', 'Criação de programas de capacitação e gestão de performance.', 4900.00, 'ALTA'),
-- Área 6: Contabilidade
(10005, 'Analista Financeiro', 'Elaboração de orçamentos, relatórios e análises de desempenho financeiro.', 4800.00, 'MEDIA'),
(10006, 'Atuário', 'Cálculo de riscos e seguros para empresas e fundos de pensão.', 5200.00, 'MEDIA'),
-- Área 8: Design
(20001, 'UX/UI Designer', 'Projeto da experiência e interface do usuário para produtos digitais.', 5000.00, 'ALTA'),
-- Área 11: Publicidade
(20002, 'Redator Publicitário (Copywriter)', 'Criação de textos persuasivos para campanhas de marketing.', 4000.00, 'MEDIA'),
-- Área 9: Comunicação
(20003, 'Diretor de Arte', 'Responsável pela concepção visual de campanhas publicitárias e projetos.', 6500.00, 'MEDIA'),
-- Área 14: Cinema
(20004, 'Produtor Audiovisual', 'Gerenciamento e execução da produção de conteúdo em vídeo e filmes.', 4500.00, 'MEDIA'),
-- Área 17: Psicologia
(30001, 'Psicólogo Clínico', 'Diagnóstico e tratamento de distúrbios emocionais e mentais em consultório.', 4200.00, 'MEDIA'),
-- Área 16: Educação
(30002, 'Professor (Ensino Médio)', 'Ensino de disciplinas específicas em escolas públicas ou privadas.', 3800.00, 'MEDIA'),
-- Área 18: Social
(30003, 'Assistente Social', 'Orientação e intervenção em questões sociais e direitos de comunidades.', 3500.00, 'BAIXA'),
-- Área 20: Pesquisa
(40001, 'Pesquisador Científico', 'Desenvolvimento de projetos de pesquisa em universidades ou laboratórios.', 5500.00, 'MEDIA'),
-- Área 21: Filosofia
(40002, 'Filósofo Analítico', 'Análise de conceitos e argumentos lógicos em academia ou consultoria.', 3900.00, 'BAIXA'),
-- Área 15: Humanas
(40003, 'Historiador', 'Pesquisa, docência ou atuação em museus e arquivos.', 3600.00, 'BAIXA'),
-- Área 1: Exatas
(40004, 'Estatístico', 'Aplicação de modelos matemáticos para análise de fenômenos.', 6200.00, 'ALTA');