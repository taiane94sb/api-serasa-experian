
# Desafio Serasa Experian
Serviço do tipo API REST, para cadastro de pessoas com score e endereço.

## Startar a aplicação:

Crie um novo arquivo jar usando o maven builder.
```bash
  ./mvnw clean package
```

Crie uma imagem docker usando o comando docker build.
```bash
  docker build -t [name:tag] .
```
```bash
  ex.: docker build -t taianes94sb/serasaexperianapi .
```
Crie um contêiner docker executando.
```bash
  docker run -d -p [host_port]:[container_port] –name [container_name] [image_id/image_tag]
```
```bash
  ex.: docker run -d -p 8080:8080 --name serasaexperianapi taianes94sb/serasaexperianapi:latest
```

Verifique se o contêiner foi criado com sucesso executando.
```bash
  docker container ps
```

Navegue até http://localhost:8080/ em seu navegador para visualizar o aplicativo Spring Boot.

## Documentação da API

### Documentação dos contratos:
#### swagger-ui
```http
  http://localhost:8080/swagger-ui/index.html
```


### Banco de dados em memória:
#### h2-console
```http
  http://localhost:8080/h2-console
```


### Autenticação:
#### Registra um novo usuário (role pode ser ADMIN ou USER), que será usado para se logar:


```http
  GET /api/auth/register
```


**Request-body:** {
  "login": "string",
  "password": "string",
  "role": "ADMIN"
}


**Request-URL:** http://localhost:8080/api/auth/register


#### Loga um usuário e retorna um token, usado na autenticação:


```http
  GET /api/auth/login
```


**Request-body:** {
  "login": "string",
  "password": "string"
}


**Request-URL:** http://localhost:8080/api/auth/login


### Endereço:
#### Retorna as informações do endereço baseado no cep fornecido


```http
  GET /api/enderecos/${cep}
```
**Request-URL:** http://localhost:8080/api/enderecos/41181025


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cep`      | `string` | **Obrigatório**. O cep da pessoa |


### Pessoa:
#### Retorna a lista de pessoas cadastradas


```http
  GET /api/pessoas/listarPessoas
```


**Request-URL:** http://localhost:8080/api/pessoas/listarPessoas


#### Retorna a lista páginada de pessoas cadastradas


```http
  GET /api/pessoas/listarPessoasPaginadas
```


**Request-URL:** http://localhost:8080/api/pessoas/listarPessoasPaginadas?page=0&size=2&sort=nome


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `page`      | `object` | **Obrigatório**. {"page": 0,"size": 2,"sort": ["nome"]} |


#### Retorna a pessoa baseado no id fornecido


```http
  GET /api/pessoas/porId/${pessoaId}
```


**Request-URL:** http://localhost:8080/api/pessoas/porId/1


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `pessoaId`      | `long` | **Obrigatório**. O id da pessoa |


#### Retorna a pessoa baseado no nome fornecido


```http
  GET /api/pessoas/porNome/${pessoaNome}
```


**Request-URL:** http://localhost:8080/api/pessoas/porNome/taiane


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `pessoaNome`      | `string` | **Obrigatório**. O nome da pessoa |


#### Retorna a pessoa baseado na idade fornecida


```http
  GET /api/pessoas/porIdade/${pessoaIdade}
```


**Request-URL:** http://localhost:8080/api/pessoas/porIdade/29


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `pessoaIdade`      | `int` | **Obrigatório**. A idade da pessoa |


#### Retorna a pessoa baseado no cep fornecido


```http
  GET /api/pessoas/porCep/${pessoaCep}
```


**Request-URL:** http://localhost:8080/api/pessoas/porCep/41181-025


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `pessoaCep`      | `string` | **Obrigatório**. O cep da pessoa |


#### Cadastra uma pessoa baseadas nas informações fornecidas


```http
  POST /api/pessoas
```
**Request-body:** {
  "nome": "matheus",
  "idade": 25,
  "cep": "41100000",
  "telefone": "999999999",
  "score": 1000
}


**Request-URL:** http://localhost:8080/api/pessoas


#### Atualiza uma pessoa baseado no id fornecido


```http
  PUT /api/pessoas/{pessoaId}
```


**Request-body:** {
  "nome": "sueli",
  "idade": 50,
  "cep": "41181025",
  "telefone": "222222222",
  "score": 999
}


**Request-URL:** http://localhost:8080/api/pessoas/2


#### Remove uma pessoa baseado no id fornecido


```http
  DELETE /api/pessoas/{pessoaId}
```


**Request-URL:** http://localhost:8080/api/pessoas/2


## 🔗 Links
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/taiane-barbosa/)
