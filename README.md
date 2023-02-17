<h1>Attornatus Backend Challenge</h1>
<p>Desafio backend para o processo de seleção da empresa Attornatus Procuradoria Digital.</p>
<h2>O Desafio</h2>
<p>Criar uma aplicação com Spring Boot para gerenciar pessoas. A api deve permitir:
<ul><li>Criar, editar e consultar uma pessoa;</li>
<li>Criar endereço para a pessoa e poder informar qual é o endereço principal da pessoa;</li>
<li>Listar pessoas;</li>
<li>Listar endereços da pessoa.</li></ul>
<h2>Tecnologias utilizadas</h2>
<ul><li>Java 17</li>
<li>Spring Boot 2.7.8</li>
<li>H2 Database</li>
<li>Lombok</li>
<li>JUnit 5</li>
<li>Spring Boot Test com Mockito</li>
<li>Swagger API</li>
<li>Model Mapper</li>
<li>AWS EC2</li></ul>
<h2>A Aplicação</h2>
<p>Pelo desafio proposto, foram criadas duas entidades Person e Address, e as tabelas relacionadas de forma que uma pessoa tenha vários endereços, e um deles como sendo o endereço principal. Foi utilizado o Lombok, diminuindo assim a verbosidade de todo o código. Foi utilizado o padrão DTO para a transferência de dados entre as camadas, e para essa conversão foi utilizado o Model Mapper. Os endpoints foram documentados com Swagger, e a classe de serviços foi testada com JUnit 5 para as assertions e o Mockito para o mock dos repositórios e sua injeção na classe de serviços testada. O deploy da aplicação foi feito a partir de uma instância EC2 da AWS, e a conexão persistida através de um script .service rodando na máquina virtual.</p>
<h2>Acessando a aplicação</h2>
<ul><li><a href="http://ec2-15-228-191-16.sa-east-1.compute.amazonaws.com:8080/swagger-ui.html#/">Documentação Swagger</a></li></ul>
<h2>Clonando o repositório</h2>
<p>Para clonar com uma chave SSH, basta dar o comando git:</p>

```sh
git clone git@github.com:marcelofgaraujo/attornatus_backend_challenge.git
```
<p>Para clonar sem uma chave SSH:</p>

```sh
git clone https://github.com/marcelofgaraujo/attornatus_backend_challenge.git
```
<h2>Licença</h2>
<p>Este repositório está sob uma <a href="https://github.com/marcelofgaraujo/attornatus_backend_challenge/blob/main/LICENSE.md">Licença MIT</a>.</p>
