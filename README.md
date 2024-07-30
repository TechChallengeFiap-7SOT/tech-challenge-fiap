
# Tech Challenge - FIAP
O projeto lanchonete foi desenvolvido para aplicar os conceitos aprendidos no curso de pós graduação em arquitetura de software da faculdade FIAP, esses conceitos consistem no desenvolvimento a partir do refinamento utilizando a metodologia de eventstorm (DDD), aplicando a arquitetura hexagonal e o docker.
Toda a documentação DDD do Event Storm para o refinamento se encontra disponível no [miro](https://miro.com/app/board/uXjVKC2h5jE=/).

## Autores
Turma: 7 SOAT.
- [@pinheirojks](https://github.com/pinheirojks)
- [@maurimurakami](https://github.com/maurimurakami)
- [@Piovezan1207](https://github.com/Piovezan1207)

# Tecnologias utilizadas
* A aplicação foi desenvolvida utilizando Java 17, framework Spring Boot, com a utilização do banco de dados MySql. Além disso foi utilizado o Docker para a execução da aplicação e do banco de dados e o Swagger para a documentação da API.

# Estrutura do projeto

```
├───adapter
│   ├───controller
│   ├───gateway
│   └───presenter
├───config
├───core
│   ├───entity
│   ├───repository
│   ├───type
│   └───usecase
└───external
    ├───handler
    │   ├───dto
    │   ├───exception
    │   └───service
    └───infra
        ├───exception
        └───persistence
            ├───entity
            └───repository
```


# Premissas para a execução do projeto.
* Ter o [docker](https://www.docker.com/products/docker-desktop/) instalado na máquina.
* Realizar o checkout da branch [Main](https://github.com/legasrossini/techchallenger-lanchonete).

# Passo a passo para a execução do projeto.
* Após o checkout da branch Main, acessar a raiz do projeto "techchallenger-lanchonete" onde se encontra o arquivo docker-compose.yml.
* Com o terminal aberto na raiz do projeto, executar o comando `docker-compose up` para iniciar a aplicação.
* Após a execução do comando, a aplicação estará disponível em [localhost:8080](localhost:8080).
* A aplicação estará disponível para testes na interface do Swagger, acessível em [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html).
* Como a aplicação foi desenvolvida utilizando o framework Spring Boot e MySql dentro do Docker, pode levar um tempo de inicialização um pouco mais longo, pois a aplicação aguarda que o banco esteja dísponivel para ser iniciada. Após a execução do comando `docker-compose up`, aguardar alguns minutos até que a aplicação esteja disponível.
* Para encerrar a execução da aplicação, basta executar o comando `docker-compose down`.

# Funcionalidades
* A seguir são apresentadas as funcionalidades presentes na API.

### Categorias
- Listagem de todas as categorias disponíveis.

### Clientes
- Cadastro de clientes
- Consulta do cliente via CPF

### Produtos
- Criar, editar e remover produtos (soft delete, onde o mesmo é desativado).
- Buscar produtos por categoria, trazendo apenas os ativos.
- Buscar todos os produtos ativos.
- Consulta de produto por ID, trazendo o registro independente de status.

### Pedidos
- Checkout do pedido, onde o mesmo é criado com o status RECEBIDO, estando assim aguardando pagamento para prosseguir às próximas etapas.
- Listar todos os pedidos, independente de status, ordenados por situação e data/hora.
- Listar todos os pedidos da fila, que contemplam as situações: RECEBIDO, EM_PREPARACAO, PRONTO e FINALIZADO.
- Consultar status de pagamento do pedido.
- Consultar detalhes do pedido por ID.
- Atualizar status do pedido.
- Atualizar status de pagamento do pedido.

### Webhook pagamentos
- Permite receber o retorno de um gateway de pagamentos para atualizar o status de pagamento do pedido. Por momento está mockado, não foi feito de fato uma integração. Para variação de cenários, aleatoriamente o Mock define se o pagamento foi aprovado ou não.

# Observações
* Para visualizar as categorias disponíveis, navegar até o grupo "Categorias" na interface acima, expandir o endpoint "GET" e clicar em "Try it out", o que habilitará o botão "Execute" para efetuar o request. Após a execução, a lista de categorias com seus respectivos IDs serão listadas na área logo abaixo ao botão "Execute".
* No CRUD de produtos, o método DELETE efetua um "soft delete", atualizando a flag "ativo" para false. Ao listar os produtos, aqueles com status "ativo = false" não serão retornados. Porém na consulta de produto por ID, mesmo que esse esteja desativado, o mesmo é retornado para fins de consulta. A listagem de categorias também só lista os produtos ativos. Por fim, somente é possível efetuar pedidos para produtos ativos.
