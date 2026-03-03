# EstoquePOO

Este é um sistema de controle de estoque e vendas desenvolvido em Java
utilizando uma arquitetura simples baseada em DAO/Service/View. O propósito
é demonstrar princípios de programação orientada a objetos (POO) e manipular
um banco de dados MySQL para armazenar clientes, produtos, usuários e vendas.

## Funcionalidades principais

- Cadastro, busca e listagem de **clientes**, **fornecedores** e **usuários**
- Controle de **produtos** com estoque, preço de compra/venda e estado ativo
- Registro de **vendas** com itens, cálculo automático de totals e histórico
- Relatórios básicos de receita, média de vendas e vendas por cliente
- Tratamento de cliente anônimo (venda sem cliente cadastrado)
- Atualização de estoque ao realizar vendas

## Estrutura do projeto

O código-fonte está em `src/main/java` organizado pelos pacotes:

- `dao`: acesso ao banco (CRUD) para cada entidade
- `model`: classes de domínio (Cliente, Produto, Venda, etc.)
- `service`: regras de negócio e validações
- `view`: menus do console que interagem com o usuário
- `util`: utilitários, incluindo a classe `Conexao` para o MySQL

A pasta `bancodedados` contém scripts SQL necessários para criar as tabelas
(ou você pode executar manualmente no MySQL).

## Requisitos

- Java 17+ (testado com JDK 21)
- MySQL 8.x com banco `estoquePOO` acessível via `usuario`/`senha` (ajustar
enquanto necessário em `Conexao.java`)
- VS Code ou outro editor/IDE para compilar e executar

## Configuração do banco

Antes de rodar a aplicação, crie o banco e execute os scripts de criação:

```sql
CREATE DATABASE estoquePOO;
USE estoquePOO;

-- tabelas básicas
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(11) UNIQUE
);
-- demais tabelas similares ...
```

Para suportar vendas com cliente anônimo, execute também:

```sql
ALTER TABLE venda MODIFY cliente_id INT NULL;
ALTER TABLE venda DROP FOREIGN KEY venda_ibfk_1;
ALTER TABLE venda
    ADD CONSTRAINT venda_ibfk_1
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
    ON DELETE SET NULL ON UPDATE CASCADE;
```

## Como usar

1. Compile o projeto (`javac`) ou use `mvn`/`gradle` se estiver configurado.
2. Inicie a aplicação executando `main.java.main.Main`.
3. Navegue pelos menus de cadastro e vendas no console.

## Observações

Desenvolvido como exercício acadêmico/educacional para exemplificar POO e
acesso a banco de dados em Java.
