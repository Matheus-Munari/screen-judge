
[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[AWS_BADGE]:https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white
[HTML5]: https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white
[Hibernate]:https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Docker]:https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white


<h1 align="center" style="font-weight: bold;">ScreenJudge 🎬</h1>

![AWS][AWS_BADGE]
![spring][SPRING_BADGE]
![java][JAVA_BADGE]
![HTML5][HTML5]
![Hibernate]
![Docker]


<p align="center">
 <a href="#setup">Setup</a> • 
  <a href="#clonar">Clonar projeto</a> •
 <a href="#variaveis">Variáveis</a> •
 <a href="#doc">Documentação</a>
</p>

<p align="center">
  <b>Este aplicativo permite que os usuários publiquem suas próprias avaliações de filmes, interajam comentando nas avaliações de outros usuários e criem listas personalizadas de recomendações. Além disso, as listas podem ser compartilhadas, proporcionando uma experiência colaborativa na descoberta de novos filmes. 🎬✨</b>
</p>

<h2 id="setup">🚀 Setup</h2>

Como utilizar este projeto localmente

<h3>Prerequisites</h3>

- Java 17
- Docker (WSL2 consequentemente se estiver em Windows)

<h3 id="clonar">Clonar projeto</h3>

How to clone your project

```bash
git clone https://github.com/Matheus-Munari/avaliador-filmes.git
```

<h3 id="variaveis"> Variáveis de Ambiente</h3>


Use o `application.yml` como referência para adicionar as suas configurações 

<h4>Configuração do email para envio de mensagens</h4>

```yaml
spring:
  mail:
    username: ${EMAIL_ENV}
    password: ${EMAIL_S_ENV}
```

Para que a aplicação mande emails automaticamente é necessário configurar um email e senha para o serviço de email

PASSO A PASSO

<h4>Configuração da API Key para comunicar com o serviço do TMDB</h4>

Para comunicação com a API do TMDB utilize as seguintes configurações no `aplication.yml`

```yml
feign:
  client:
    config:
      tmdb:
        url: https://api.themoviedb.org/3
        key: ${TMDB_API_KEY}
```

Para conseguir utilizar os endpoints de busca de filmes configure a TMDB_API_KEY seguindo o passo a passo a baixo

<a href="https://scribehow.com/shared/Creating_An_Account_On_The_Movie_Database_TMDB__3BoDCb22QMCV4ax1vvLqfA">Acesse sua API Key do TMDB</a>

<h4>Configuração do container de Kafka</h4>

Dado que você possua o Docker instalado

Navegue até a pasta "kafka" e execute o comando para subir um container de kafka

```bash
git compose up -d
```

Uma vez que o conteiner esteja criado e funcionando (Comando para verificar status do container)

```bash
docker ps -a
```

Acesse o container

```bash
docker exec --workdir /opt/kafka/bin/ -it broker sh
```

Crie 2 tópicos ("email-sender" e "comentario_avaliacao_email")

```bash
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic email-sender
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic comentario_avaliacao_email
```

Crie 2 consumidores ("comentario_avaliacao_consumer" e "meu-grupo")

```bash
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic teste-topico --group comentario_avaliacao_consumer -
-consumer-property client.id=comentario_avaliacao_consumer

./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic teste-topico --group meu-grupo -
-consumer-property client.id=meu-grupo
```

<h3>Starting</h3>

Abra sua IDE e execute o projeto a partir da classe `AvaliadorApplication`


<h2 id="doc">📍 Documentação API</h2>

Lista de Endpoints da API, seus payloads e repostas possíveis disponíveis em `http://localhost:8080/swagger-ui/index.html` com o projeto rodando



