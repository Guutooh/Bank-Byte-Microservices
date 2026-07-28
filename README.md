# EazyBank — Arquitetura de Microserviços com Spring Cloud

Plataforma bancária baseada em microserviços, desenvolvida com Java 21, Spring Boot 4 e Spring Cloud. A arquitetura cobre o ciclo completo de uma aplicação distribuída em produção: configuração centralizada, descoberta de serviços, comunicação síncrona e assíncrona, segurança com OAuth2, resiliência, observabilidade e orquestração em Kubernetes.

---

## O que o sistema faz

Três serviços de domínio independentes — contas, empréstimos e cartões — sustentados por uma infraestrutura completa de microserviços. Cada serviço tem seu próprio banco, suas próprias regras e seu próprio ciclo de vida. A comunicação entre eles passa pelo gateway, a configuração vem de um servidor centralizado, os eventos trafegam via Kafka e tudo é monitorado por uma stack de observabilidade com Grafana.

---

## Serviços

**Domínio**

- `accounts` — gestão de clientes e contas bancárias; ponto de entrada principal, agrega dados de loans e cards via Feign
- `loans` — controle de empréstimos
- `cards` — gestão de cartões de crédito
- `message` — processa eventos assíncronos via Kafka

**Infraestrutura**

- `configserver` — centraliza todas as configurações por ambiente (default, qa, prod)
- `eurekaserver` — registro e descoberta de serviços
- `gatewayserver` — ponto de entrada único; roteamento, circuit breaker e autenticação OAuth2

---

## Tecnologias

**Back-end**

- Java 21 + Spring Boot 4
- Spring Cloud Config — configuração externalizada por ambiente
- Spring Cloud Netflix Eureka — service discovery
- Spring Cloud Gateway — roteamento dinâmico, rate limiting com Redis, circuit breaker
- OpenFeign — comunicação declarativa entre serviços
- Resilience4j — circuit breaker, retry e rate limiter
- Spring Cloud Stream + Apache Kafka — mensageria assíncrona entre serviços
- Spring Security + OAuth2 Resource Server — segurança via Keycloak
- MapStruct — mapeamento de objetos em tempo de compilação
- JPA/Hibernate + H2 — persistência

**Observabilidade**

- OpenTelemetry — instrumentação e propagação de traces
- Micrometer — coleta de métricas na camada da aplicação
- Grafana Tempo — rastreamento distribuído
- Grafana Loki — agregação de logs
- Prometheus — métricas
- Grafana — dashboards unificados
- Grafana Alloy — agente de coleta

**Infraestrutura e deploy**

- Docker + Docker Compose — ambiente local completo
- Kubernetes — deploy em cluster com manifestos YAML
- Helm — charts para gerenciar o deploy no Kubernetes
- Google Cloud Platform (GCP) + Google Kubernetes Engine — deploy em nuvem
- Google Jib — build de imagens Docker sem Dockerfile
- Buildpacks — empacotamento de aplicações
- Maven BOM compartilhado (`eazy-bom`) para versionamento consistente entre serviços

**Documentação**

- OpenAPI Specification + Swagger — documentação das APIs REST

---

## Como rodar localmente

Você vai precisar de Java 21+, Docker e Docker Compose instalados.

**1. Sobe toda a infraestrutura com Docker Compose**

```bash
cd docker-compose/default
docker-compose up -d
```

Os serviços sobem na ordem certa por dependência: Config Server → Eureka → serviços de domínio → Gateway.

**2. Principais portas**

| Serviço       | Porta |
|---------------|-------|
| Config Server | 8071  |
| Eureka        | 8070  |
| Gateway       | 8072  |
| Accounts      | 8080  |
| Loans         | 8090  |
| Cards         | 9000  |
| Keycloak      | 7080  |
| Grafana       | 3000  |
| Prometheus    | 9090  |

**3. Para rodar os serviços localmente (sem Docker)**

```bash
cd <nome-do-servico>
./mvnw spring-boot:run
```

O `application.yml` de cada serviço aponta para o Config Server em `localhost:8071` e para o Eureka em `localhost:8070`.

---

## Deploy no Kubernetes

**Com manifestos YAML**

```bash
kubectl apply -f kubernetes/1_keycloak.yml
kubectl apply -f kubernetes/2_configmaps.yaml
kubectl apply -f kubernetes/3_configserver.yml
kubectl apply -f kubernetes/4_eurekaserver.yml
kubectl apply -f kubernetes/5_accounts.yml
kubectl apply -f kubernetes/6_loans.yml
kubectl apply -f kubernetes/7_cards.yml
kubectl apply -f kubernetes/8_gateway.yml
```

**Com Helm**

```bash
cd helm/environments/dev-env
helm dependency build
helm install eazybank .
```

---

## Principais comandos do Kubernetes

| Comando | Descrição |
|---------|-----------|
| `kubectl apply -f filename` | Cria recursos a partir de um arquivo YAML |
| `kubectl get all` | Lista todos os componentes do cluster |
| `kubectl get pods` | Lista todos os pods |
| `kubectl describe pod pod-id` | Detalhes de um pod específico |
| `kubectl delete pod pod-id` | Remove um pod do cluster |
| `kubectl get services` | Lista todos os serviços |
| `kubectl get deployments` | Lista todos os deployments |
| `kubectl get configmaps` | Lista todos os configmaps |
| `kubectl scale deployment accounts-deployment --replicas=1` | Escala um deployment |
| `kubectl set image deployment gatewayserver-deployment gatewayserver=eazybytes/gatewayserver:s11` | Atualiza a imagem de um deployment |
| `kubectl rollout history deployment gatewayserver-deployment` | Histórico de rollout |
| `kubectl rollout undo deployment gatewayserver-deployment --to-revision=1` | Reverte para uma revisão anterior |
| `kubectl get events --sort-by=.metadata.creationTimestamp` | Lista eventos do cluster ordenados por data |
| `kubectl get pvc` | Lista os PersistentVolumeClaims |

---

## Principais comandos do Helm

| Comando | Descrição |
|---------|-----------|
| `helm create [NAME]` | Cria um chart padrão |
| `helm dependencies build` | Reconstrói as dependências do chart |
| `helm install [NAME] [CHART]` | Instala um chart no cluster |
| `helm upgrade [NAME] [CHART]` | Atualiza um release existente |
| `helm history [NAME]` | Exibe o histórico de revisões de um release |
| `helm rollback [NAME] [REVISION]` | Faz rollback para uma revisão anterior |
| `helm uninstall [NAME]` | Remove todos os recursos de um release |
| `helm template [NAME] [CHART]` | Renderiza os templates localmente |
| `helm list` | Lista todos os releases no cluster |

---

## Estrutura de cada serviço

```
src/main/java/com/eazybytes/<servico>/
├── controller/     → endpoints REST
├── service/        → regras de negócio e clientes Feign
├── entity/         → entidades JPA
├── dto/            → objetos de transferência
├── repository/     → interfaces JPA
├── exception/      → tratamento centralizado de erros
└── functions/      → funções para integração com Kafka (Spring Cloud Stream)
```

---

## Referência

Projeto desenvolvido seguindo o curso: https://www.udemy.com/course/master-microservices-with-spring-docker-kubernetes/

O foco foi entender na prática como construir e operar uma arquitetura de microserviços completa — do primeiro serviço isolado até um ambiente com observabilidade, segurança e deploy orquestrado em Kubernetes.
