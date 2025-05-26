USE master;
GO

DROP DATABASE IF EXISTS Restaurante;
GO

CREATE DATABASE Restaurante;
GO

USE Restaurante;
GO

-- Criação da tabela Funcionario
CREATE TABLE Funcionario (
    idFuncionario INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    nomeFuncionario VARCHAR(99) NOT NULL,
    senha VARCHAR(69) NOT NULL,
    verificarAcesso VARCHAR(10) NOT NULL,
    cpfFuncionario VARCHAR(11) NOT NULL UNIQUE,
    emailFuncionario VARCHAR(255) NOT NULL,
    telefoneFuncionario VARCHAR(11) NOT NULL,
    generoFuncionario VARCHAR(10) NOT NULL,
    enderecoFuncionario VARCHAR(255) NOT NULL,
    dataNascFuncionario DATE NOT NULL,
    cargo VARCHAR(40) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    dataDeAdmissao DATE NOT NULL
);
GO

-- Criação da tabela Fornecedor
CREATE TABLE Fornecedor (
    idFornecedor INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    nomeFornecedor VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    endereco VARCHAR(255) NOT NULL
);
GO

-- Criação da tabela Mesa
CREATE TABLE Mesa (
    idMesa INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    capacidade INT NOT NULL,
    condicao VARCHAR(40) NOT NULL
);
GO

-- Criação da tabela Produto
CREATE TABLE Produto (
    idProduto INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    codeFornecedor INT NOT NULL,
    nomeProduto VARCHAR(255) NOT NULL,
    dataFabricacao DATE NOT NULL,
    dataValidade DATE NOT NULL,
    marca VARCHAR(20) NOT NULL,
    categoria VARCHAR(35) NOT NULL,
    precoUnitario DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL,
    CONSTRAINT FK_Produto_Fornecedor FOREIGN KEY (codeFornecedor) REFERENCES Fornecedor(idFornecedor)
);
GO

-- Criação da tabela Pedido
CREATE TABLE Pedido (
    idPedido INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    codeFuncionario INT NOT NULL,
    codeMesa INT NOT NULL,
    dataPedido DATE NOT NULL,
    condicao VARCHAR(40) NOT NULL,
    observacoes VARCHAR(100),
    desconto DECIMAL(10,2) DEFAULT 0,
    precoTotal DECIMAL(10,2) DEFAULT 0,
    CONSTRAINT FK_Pedido_Funcionario FOREIGN KEY (codeFuncionario) REFERENCES Funcionario(idFuncionario),
    CONSTRAINT FK_Pedido_Mesa FOREIGN KEY (codeMesa) REFERENCES Mesa(idMesa)
);
GO

-- Criação da tabela Cardapio
CREATE TABLE Cardapio (
    idCardapio INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    nomeCardapio VARCHAR(50) NOT NULL,
    descricao VARCHAR(100),
    categoria VARCHAR(50),
    precoUnitario DECIMAL(10,2) NOT NULL
);
GO

-- Criação da tabela Produto_Cardapio
CREATE TABLE Produto_Cardapio (
    idProdutoCardapio INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    codeCardapio INT NOT NULL,
    codeProduto INT NOT NULL,
    quantidade INT NOT NULL,
    CONSTRAINT FK_ProdutoCardapio_Cardapio FOREIGN KEY (codeCardapio) REFERENCES Cardapio(idCardapio),
    CONSTRAINT FK_ProdutoCardapio_Produto FOREIGN KEY (codeProduto) REFERENCES Produto(idProduto)
);
GO

-- Criação da tabela Cardapio_Pedido
CREATE TABLE Cardapio_Pedido (
    idCardapioPedido INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    codeCardapio INT NOT NULL,
    codePedido INT NOT NULL,
	observacao varchar(100),
    quantidade INT NOT NULL,
    CONSTRAINT FK_CardapioPedido_Cardapio FOREIGN KEY (codeCardapio) REFERENCES Cardapio(idCardapio),
    CONSTRAINT FK_CardapioPedido_Pedido FOREIGN KEY (codePedido) REFERENCES Pedido(idPedido)
);
GO

-- Trigger para aplicar desconto no Pedido (AFTER INSERT)
CREATE TRIGGER TR_AplicarDesconto
ON Pedido
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1

        FROM inserted i
		WHERE i.desconto IS NOT NULL
		AND i.precoTotal IS NOT NULL
          AND i.desconto > i.precoTotal
    )
    BEGIN
        RAISERROR('Desconto não pode ser maior que o precoTotal.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;

    UPDATE p
    SET p.precoTotal = p.precoTotal - ISNULL(i.desconto, 0)
    FROM Pedido p
    INNER JOIN inserted i ON p.idPedido = i.idPedido;
END;
GO

-- Trigger para atualizar o precoTotal do Pedido e ajustar o estoque (AFTER INSERT em Cardapio_Pedido)
CREATE TRIGGER TR_AtualizarPrecoTotal_E_AtualizarEstoque
ON Cardapio_Pedido
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Verifica se algum item inserido tem precoUnitario nulo
    IF EXISTS (
        SELECT 1
        FROM inserted ip
        LEFT JOIN Cardapio c ON ip.codeCardapio = c.idCardapio
        WHERE c.precoUnitario IS NULL
    )
    BEGIN
        RAISERROR('Cardapio não encontrado ou precoUnitario nulo.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;

    -- Verifica estoque suficiente para todos os produtos usados nos pedidos inseridos
    IF EXISTS (
        SELECT 1
        FROM (
            SELECT p.idProduto,
                   p.estoque,
                   SUM(pc.quantidade * ip.quantidade) AS total_necessario
            FROM inserted ip
            INNER JOIN Produto_Cardapio pc ON ip.codeCardapio = pc.codeCardapio
            INNER JOIN Produto p ON p.idProduto = pc.codeProduto
            GROUP BY p.idProduto, p.estoque
        ) AS verifica
        WHERE verifica.estoque < verifica.total_necessario
    )
    BEGIN
        RAISERROR('Estoque insuficiente para realizar a operação.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;

    -- Atualiza o precoTotal por pedido, calculando soma dos produtos * quantidade e subtraindo desconto do pedido
    UPDATE p
    SET p.precoTotal = ISNULL(comp.valorTotal, 0) - ISNULL(p.desconto, 0)
    FROM Pedido p
    INNER JOIN (
        SELECT cp.codePedido,
               SUM(c.precoUnitario * cp.quantidade) AS valorTotal
        FROM Cardapio_Pedido cp
        INNER JOIN Cardapio c ON cp.codeCardapio = c.idCardapio
        WHERE cp.codePedido IN (SELECT DISTINCT codePedido FROM inserted)
        GROUP BY cp.codePedido
    ) AS comp ON p.idPedido = comp.codePedido
    WHERE p.idPedido IN (SELECT DISTINCT codePedido FROM inserted);

    -- Atualiza o estoque dos produtos usados
    UPDATE prod
    SET prod.estoque = prod.estoque - ajuste.totalUsado
    FROM Produto prod
    INNER JOIN (
        SELECT pc.codeProduto, SUM(pc.quantidade * ip.quantidade) AS totalUsado
        FROM inserted ip
        INNER JOIN Produto_Cardapio pc ON ip.codeCardapio = pc.codeCardapio
        GROUP BY pc.codeProduto
    ) AS ajuste ON prod.idProduto = ajuste.codeProduto;
END

GO

-- Trigger para atualizar o precoTotal e ajustar o estoque (AFTER UPDATE em Cardapio_Pedido)
CREATE TRIGGER TR_AtualizarPrecoTotal_E_AtualizarEstoque_Update
ON Cardapio_Pedido
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE p
    SET p.precoTotal = comp.valorTotal - ISNULL(p.desconto, 0)
    FROM Pedido p
    INNER JOIN (
        SELECT cp.codePedido,
               SUM(c.precoUnitario * cp.quantidade) AS valorTotal
        FROM Cardapio_Pedido cp
        INNER JOIN Cardapio c ON cp.codeCardapio = c.idCardapio
        WHERE cp.codePedido IN (SELECT DISTINCT codePedido FROM inserted)
        GROUP BY cp.codePedido
    ) comp ON p.idPedido = comp.codePedido;

    IF EXISTS (
      SELECT 1 
      FROM (
          SELECT pc.codeProduto, SUM(pc.quantidade * (i.quantidade - d.quantidade)) AS totalDiff
          FROM inserted i
          INNER JOIN deleted d ON i.idCardapioPedido = d.idCardapioPedido
          INNER JOIN Produto_Cardapio pc ON i.codeCardapio = pc.codeCardapio
          GROUP BY pc.codeProduto
      ) AS diff
      INNER JOIN Produto p ON diff.codeProduto = p.idProduto
      WHERE p.estoque - diff.totalDiff < 0
    )
    BEGIN
       RAISERROR('Estoque insuficiente para realizar a operação de UPDATE.', 16, 1);
       ROLLBACK TRANSACTION;
       RETURN;
    END;

    UPDATE p
    SET p.estoque = p.estoque - diff.totalDiff
    FROM Produto p
    INNER JOIN (
          SELECT pc.codeProduto, SUM(pc.quantidade * (i.quantidade - d.quantidade)) AS totalDiff
          FROM inserted i
          INNER JOIN deleted d ON i.idCardapioPedido = d.idCardapioPedido
          INNER JOIN Produto_Cardapio pc ON i.codeCardapio = pc.codeCardapio
          GROUP BY pc.codeProduto
    ) AS diff ON p.idProduto = diff.codeProduto;
END;
GO

-- Trigger para atualizar o precoTotal e restaurar o estoque (AFTER DELETE em Cardapio_Pedido)
CREATE TRIGGER TR_AtualizarPrecoTotal_E_AtualizarEstoque_Delete
ON Cardapio_Pedido
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;

    -- Atualiza o precoTotal com base nos itens restantes
    UPDATE p
    SET p.precoTotal = ISNULL(comp.valorTotal, 0) - ISNULL(p.desconto, 0)
    FROM Pedido p
    INNER JOIN (
        SELECT cp.codePedido,
               SUM(c.precoUnitario * cp.quantidade) AS valorTotal
        FROM Cardapio_Pedido cp
        INNER JOIN Cardapio c ON cp.codeCardapio = c.idCardapio
        WHERE cp.codePedido IN (SELECT DISTINCT codePedido FROM deleted)
        GROUP BY cp.codePedido
    ) comp ON p.idPedido = comp.codePedido;

    -- Zera o precoTotal se o pedido não tiver mais itens
    UPDATE p
    SET p.precoTotal = 0 - ISNULL(p.desconto, 0)
    FROM Pedido p
    WHERE p.idPedido IN (SELECT DISTINCT codePedido FROM deleted)
      AND NOT EXISTS (
          SELECT 1 FROM Cardapio_Pedido cp WHERE cp.codePedido = p.idPedido
      );

    -- Restaura o estoque dos produtos envolvidos
    UPDATE prod
    SET prod.estoque = prod.estoque + ajuste.totalRestaurado
    FROM Produto prod
    INNER JOIN (
        SELECT pc.codeProduto, SUM(pc.quantidade * d.quantidade) AS totalRestaurado
        FROM deleted d
        INNER JOIN Produto_Cardapio pc ON d.codeCardapio = pc.codeCardapio
        GROUP BY pc.codeProduto
    ) ajuste ON prod.idProduto = ajuste.codeProduto;
END;


GO

-- Trigger para alerta de estoque
CREATE TRIGGER TR_AlertaEstoque
ON Produto
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    
    IF EXISTS (
        SELECT 1 
        FROM inserted i
        INNER JOIN deleted d ON i.idProduto = d.idProduto
        WHERE i.estoque <= 10 AND d.estoque > 10
    )
    BEGIN
        DECLARE @nomeProduto VARCHAR(255);
        SELECT TOP 1 @nomeProduto = nomeProduto
        FROM inserted
        WHERE estoque <= 10;
        
        RAISERROR('Atenção: o produto %s está com estoque crítico (<= 10).', 16, 1, @nomeProduto);
    END;
END;
GO

-- Inserções na tabela Funcionario com verificarAcesso
INSERT INTO Funcionario (nomeFuncionario, senha, verificarAcesso, cpfFuncionario, emailFuncionario, telefoneFuncionario, generoFuncionario, enderecoFuncionario, dataNascFuncionario, cargo, salario, dataDeAdmissao)
VALUES 
('João Pedro Ferreira Menezes', 'joao@2023', '1', '12345678909', 'joao.almeida@restaurante.com', '11912345678', 'Masculino', 'Av. das Américas, 100', '1980-03-12', 'Cuzinheira', 2100.00, '2020-02-15'),
('Arthur Pinheiro', 'arthur6969', '2', '98765432100', 'maria.ferreira@restaurante.com', '11923456789', 'Feminino', 'Rua do Mercado, 45', '1985-07-22', 'Jogador de Tomate 🍅', 2600.00, '2019-06-20'),
('Garçon', 'garcon', '3', '11122233396', 'carlos.santos@restaurante.com', '11934567890', 'Masculino', 'Alameda dos Anjos, 300', '1990-11-05', 'Garçom', 1900.00, '2021-03-10'),
('Ana Ribeiro', 'ana!abc', '3', '22233344405', 'ana.ribeiro@restaurante.com', '11945678901', 'Feminino', 'Rua da Liberdade, 150', '1992-05-18', 'Garçom', 5200.00, '2018-09-05'),
('Pedro Lima', 'pedro2023', '3', '33344455508', 'pedro.lima@restaurante.com', '11956789012', 'Masculino', 'Av. Brasil, 500', '1988-02-28', 'Garçom', 2400.00, '2022-01-25'),
('Luiza Costa', 'luiza*cost', '3', '44455566619', 'luiza.costa@restaurante.com', '11967890123', 'Feminino', 'Rua das Palmeiras, 89', '1995-12-02', 'Garçonete', 2200.00, '2021-07-14'),
('Roberto Pereira', 'roberto@321', '3', '55566677720', 'roberto.pereira@restaurante.com', '11978901234', 'Masculino', 'Av. Central, 250', '1983-04-30', 'Garçom', 2000.00, '2020-04-20'),
('Fernanda Souza', 'fernanda#654', '3', '66677788830', 'fernanda.souza@restaurante.com', '11989012345', 'Feminino', 'Rua das Oliveiras, 77', '1991-08-15', 'Garçonete', 2500.00, '2019-11-01'),
('Ricardo Gomes', 'ricardo@987', '3', '77788899941', 'ricardo.gomes@restaurante.com', '11990123456', 'Masculino', 'Alameda Verde, 210', '1987-06-10', 'Garçom', 2300.00, '2022-05-18'),
('Beatriz Nunes', 'beatriz*2023', '3', '88899900078', 'beatriz.nunes@restaurante.com', '11901234567', 'Feminino', 'Av. das Estrelas, 99', '1993-10-12', 'Garçom', 2100.00, '2023-03-05');
GO

-- Inserções na tabela Fornecedor
INSERT INTO Fornecedor (nomeFornecedor, cnpj, telefone, endereco)
VALUES 
('Sabor & Cia', '12345678000195', '11911223344', 'Av. Paulista, 1000'),
('Distribuidora do Sul', '22345678000196', '21922334455', 'Rua das Laranjeiras, 200'),
('Comercial Gourmet', '32345678000197', '11933445566', 'Rua dos Pinheiros, 300'),
('Alimentos Premium', '42345678000198', '31944556677', 'Av. Central, 400'),
('Importadora Saborosa', '52345678000199', '11955667788', 'Rua Norte, 500');
GO

-- Inserções na tabela Mesa
INSERT INTO Mesa (capacidade, condicao)
VALUES 
  (4,  'Livre'),
  (6,  'Livre'),
  (2,  'Reservada'),
  (8,  'Livre'),
  (6, 'Livre'),
  (4,  'Livre'),
  (6,  'Reservada'),
  (2,  'Livre'),
  (8,  'Livre'),
  (4, 'Livre');
GO

-- Inserções na tabela Produto
INSERT INTO Produto (codeFornecedor, nomeProduto, dataFabricacao, dataValidade, marca, categoria, precoUnitario, estoque)
VALUES 
(1, 'Água Mineral Vitalis', '2023-01-10', '2025-07-10', 'Vitalis', 'Água', 2.50, 120),
(2, 'Cerveja Artesanal Pilsen', '2023-03-20', '2025-09-20', 'Brauhaus', 'Cerveja', 5.50, 85),
(3, 'Refrigerante Tradicional Cola', '2023-04-15', '2025-09-15', 'Coca-Cola', 'Refrigerante', 4.75, 10),
(4, 'Suco Natural de Laranja', '2023-05-10', '2025-05-10', 'Tropicana', 'Suco', 6.20, 30),
(5, 'Água com Gás Efervescente', '2023-06-05', '2025-06-05', 'Soda', 'Água', 3.10, 95);
GO

-- Inserções na tabela Pedido
INSERT INTO Pedido (codeFuncionario, codeMesa, dataPedido, condicao, observacoes, desconto, precoTotal)
VALUES 
(3, 7, '2023-09-10', 'Concluído', 'Cliente habitual, pedido rápido', 5.00, 165.00),
(1, 4, '2023-09-11', 'Concluído', 'Verificar conta duplicada', 8.00, 225.00),
(8, 2, '2023-09-12', 'Concluído', 'Serviço exemplar', 0.00, 185.00),
(10, 8, '2023-09-13', 'Cancelado', 'Pedido cancelado após confirmação', 0.00, 100.00),
(5, 6, '2023-09-14', 'Concluído', 'Pagamento parcelado, atenção ao troco', 12.00, 145.00),
(2, 3, '2023-09-15', 'Concluído', 'Pedido normal, sem observações', 0.00, 210.00),
(7, 1, '2023-09-16', 'Concluído', 'Aplicar desconto especial', 18.00, 105.00),
(4, 10, '2023-09-17', 'Concluído', 'Atendimento rápido e eficaz', 0.00, 260.00),
(9, 9, '2023-09-18', 'Cancelado', 'Cliente foi expulso ;-;', 5.00, 135.00),
(6, 5, '2023-09-19', 'Concluído', 'Feedback positivo do cliente', 0.00, 195.00);
GO

-- Inserções na tabela Cardapio
INSERT INTO Cardapio (nomeCardapio, descricao, categoria, precoUnitario)
VALUES 
('Hamburguer Clássico', 'Pão, carne suculenta, alface, tomate e queijo', 'Sanduíches', 16.00),
('Pizza Margherita', 'Mussarela, tomate fresco e manjericão', 'Pizzas', 26.50),
('Salada Caesar', 'Alface, croutons, parmesão e molho especial', 'Saladas', 13.00),
('Sopa de Legumes', 'Sopa cremosa com legumes variados', 'Sopas', 11.00),
('Prato Executivo', 'Arroz, feijão, bife acebolado e salada', 'Pratos Principais', 22.00),
('Combo Fast Food', 'Hamburguer, batata frita e refrigerante', 'Combos', 19.50),
('Pastel de Carne', 'Pastel crocante com recheio de carne temperada', 'Pastéis', 9.00),
('Sushi Especial', 'Seleção variada de sushi e sashimi', 'Japonesa', 32.00),
('Sobremesa de Chocolate', 'Delicioso bolo de chocolate com sorvete', 'Sobremesas', 8.00),
('Café Expresso', 'Café forte e encorpado', 'Bebidas', 5.50);
GO

-- Inserções na tabela Produto_Cardapio
INSERT INTO Produto_Cardapio (codeCardapio, codeProduto, quantidade)
VALUES 
(1, 3, 2),
(2, 5, 1),
(3, 1, 3),
(4, 2, 1),
(5, 4, 2);
GO

-- Inserções na tabela Cardapio_Pedido
INSERT INTO Cardapio_Pedido (codeCardapio, codePedido, observacao,quantidade)
VALUES 
(1, 3, 'sem cebola',2),
(2, 5, null,1),
(3, 7, 'adicional de carne',3),
(4, 2, 'com salada de coisas,',1),
(5, 8, null,2),
(6, 4, null,1),
(7, 9, null,2),
(8, 1,null ,3),
(9, 10,null ,1),
(10, 6,null ,1);
GO

-- Criação da View RegistroVendas
CREATE VIEW RegistroVendas AS
SELECT
    CP.idCardapioPedido AS idCardapioPedido,
    P.idPedido AS Numero_Pedido,
    F.nomeFuncionario AS Nome_Funcionario,
    M.idMesa AS Numero_Mesa,
    CP.codeCardapio AS Codigo_Cardapio,
    C.nomeCardapio AS Nome_Cardapio,
    CP.observacao AS Observacao,
    CP.quantidade AS Quantidade,
    C.precoUnitario AS Preco_Unitario,
    -- Cálculo do total do item SEM desconto
    CAST((C.precoUnitario * CP.quantidade) AS DECIMAL(10,2)) AS Preco_Total
FROM Pedido P
INNER JOIN Funcionario F ON P.codeFuncionario = F.idFuncionario
INNER JOIN Mesa M ON P.codeMesa = M.idMesa
INNER JOIN Cardapio_Pedido CP ON P.idPedido = CP.codePedido
INNER JOIN Cardapio C ON CP.codeCardapio = C.idCardapio;
GO


ALTER TABLE CARDAPIO ADD ativo BIT DEFAULT 1;
GO

UPDATE Cardapio SET ATIVO = 1 WHERE ATIVO IS NULL
GO

ALTER TABLE FORNECEDOR ADD ATIVO BIT DEFAULT 1;
GO

UPDATE Fornecedor SET ATIVO = 1 WHERE ATIVO IS NULL
GO

ALTER TABLE FUNCIONARIO ADD ATIVO BIT DEFAULT 1;
GO

UPDATE Funcionario SET ATIVO = 1 WHERE ATIVO IS NULL
GO

ALTER TABLE PRODUTO ADD ATIVO BIT DEFAULT 1
GO

UPDATE Produto SET ATIVO = 1 WHERE ATIVO IS NULL
GO

ALTER TABLE MESA ADD ATIVO BIT DEFAULT 1
GO

UPDATE MESA SET ATIVO = 1 WHERE ATIVO IS NULL
GO

UPDATE Pedido
SET dataPedido = GETDATE(), condicao = 'Concluído'
WHERE idPedido IN (1, 3, 5, 6, 8, 10);
GO

-- Exemplo de consulta do total de vendas do mês atual
SELECT 
    ISNULL(SUM(precoTotal), 0) AS totalVendasMes
FROM Pedido
WHERE 
    MONTH(dataPedido) = MONTH(GETDATE())
    AND YEAR(dataPedido) = YEAR(GETDATE())
    AND condicao = 'Concluído';
GO

UPDATE Pedido
SET dataPedido = GETDATE(), condicao = 'Concluído'
WHERE idPedido IN (1, 3, 5, 6, 8, 10);
GO

-- Consulta da View RegistroVendas
SELECT * FROM RegistroVendas;
GO

SELECT * FROM RegistroVendas

-- Consulta completa da tabela Produto
SELECT * FROM Cardapio_Pedido;

SELECT * FROM Pedido

SELECT * FROM MESA

SElect * from Funcionario

SELECT * FROM Cardapio

Insert into Cardapio_Pedido VALUES
(3, 3, null, 2)

DELETE FROM Cardapio_Pedido WHERE idCardapioPedido = 11

DELETE FROM Pedido WHERE idPedido = 16