## Acesso à aplicação

A api está dentro do contexto `/willbank` e os endpoints estão listados em `/willbank/swagger-ui/index.html`.
Se a porta que está rodando o Docker for 8002, por exemplo, a URL será http://localhost:8002/willbank/swagger-ui/index.html

## Docker

Criar imagem: `docker build -t willbank/projeto`

Rodar imagem na porta 8002: `docker run -p 8002:8080 willbank/projeto`