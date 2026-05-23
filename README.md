# User Management Service (projetoUsuarios)

## 1. Descrição Funcional
* **Nome do microsserviço:** `projetoUsuarios`
* **Objetivo e responsabilidades principais:** Este microsserviço é uma API REST desenvolvida em Java com Spring Boot voltada para o gerenciamento de usuários. Suas principais responsabilidades são o cadastro (salvar), listagem, edição e exclusão de usuários do sistema, garantindo a validação dos dados antes de persistir as informações no banco de dados.

## 2. Endpoints da API
O serviço expõe os seguintes endpoints principais na raiz `/usuarios`:

* **Método HTTP:** `GET`
* **URL:** `/usuarios`
* **Descrição da operação:** Retorna a lista completa de todos os usuários cadastrados.

* **Método HTTP:** `POST`
* **URL:** `/usuarios`
* **Descrição da operação:** Cria e persiste um novo usuário.

* **Método HTTP:** `PUT`
* **URL:** `/usuarios`
* **Descrição da operação:** Atualiza as informações de um usuário existente.

* **Método HTTP:** `DELETE`
* **URL:** `/usuarios/{id}`
* **Descrição da operação:** Remove um usuário do sistema com base no ID fornecido.

## 3. Exemplo de Requisição e Resposta
**Requisição de Entrada (POST `/usuarios`) - JSON:**

```json
{
  "nome": "Heitor Martini",
  "email": "heitor@exemplo.com",
  "senha": "senhaSegura123",
  "telefone": "(11) 99999-9999"
}
```

**Resposta de Saída (JSON - HTTP 201 Created):**

```json
{
  "id": 1,
  "nome": "Heitor Martini",
  "email": "heitor@exemplo.com",
  "senha": "senhaSegura123",
  "telefone": "(11) 99999-9999"
}
```

## 4. Dependências Externas
O projeto utiliza o gerenciador de dependências Maven (`pom.xml`):

* **Framework Base:** Spring Boot Starter Web.
* **Banco de Dados:** H2 Database (banco de dados em memória configurado para desenvolvimento/testes rápidos).
* **Persistência:** Spring Boot Starter Data JPA (Hibernate).
* **Ferramentas Auxiliares:** Spring Boot DevTools.

## 5. Responsável pelo Serviço
* **Equipe ou pessoa responsável:** Heitor Martini.

## 6. Procedimentos Básicos de Operação
* **Como executar localmente:**
  * Certifique-se de ter o **Java 11+** e o **Maven** instalados na máquina.
  * Baixe as dependências executando: `mvn clean install`.
  * Inicie a aplicação com o comando: `mvn spring-boot:run`.
  * A API estará disponível no endereço: `http://localhost:8080/usuarios`.
* **Como verificar logs:** Os logs detalhados do Spring Framework e das consultas SQL do Hibernate são exibidos diretamente no console/terminal de execução (`stdout`).
* **Endpoint de health check:** Por padrão do Spring Boot Starter Web, o status de execução pode ser monitorado pela resposta ativa do endpoint raiz ou via console em tempo de execução.
* **Como reiniciar o serviço:** Interrompa a execução no terminal utilizando `Ctrl + C` e execute novamente `mvn spring-boot:run`.

## 7. Regras de Negócio
* **Obrigatoriedade de Atributos:** Todo usuário gerado no sistema deve possuir obrigatoriamente os campos identificadores preenchidos (`nome`, `email`, `senha` e `telefone`) para evitar cadastros inconsistentes no banco de dados.
* **Geração de ID:** O identificador (`id`) do usuário é gerado de forma automática e sequencial pela estratégia de identidade do banco de dados ao salvar o registro.

## 8. Eventos Publicados ou Consumidos
* **Eventos:** Não aplicável. O microsserviço opera em arquitetura síncrona baseada puramente em requisições e respostas HTTP (REST API).

## 9. Métricas Monitoradas
* **Tempo de Inicialização do Contexto:** Monitoramento do tempo que o Spring Boot leva para subir o servidor TomCat interno e carregar os Beans (visível no log de inicialização).
* **Persistência de Dados:** Volume de registros ativos alocados no banco de dados em memória H2 durante o ciclo de vida da aplicação.

## 10. ADR Relacionado
* **Decisão Arquitetural:** Uso do Framework Spring Boot com Banco em Memória (H2).
* **Contexto e Justificativa:** A escolha do Spring Boot e do ecossistema Spring Data JPA foi motivada pela rapidez de desenvolvimento de APIs REST estáveis. A adoção do banco H2 permite que o microsserviço seja testado e executado localmente sem a necessidade de instalar ou configurar uma instância pesada de banco de dados externo, tornando o ambiente portátil e ideal para validações rápidas.
