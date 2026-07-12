# Task Tracker CLI

Uma aplicação de linha de comando (CLI) para gerenciar tarefas, escrita em Java puro — sem bibliotecas externas. Os dados são persistidos em um arquivo JSON local (`tasks.json`), com serialização e parsing de JSON implementados manualmente.

Projeto baseado no desafio [Task Tracker](https://roadmap.sh/projects/task-tracker) do roadmap.sh.

## Funcionalidades

- Adicionar, atualizar e remover tarefas
- Marcar tarefa como em andamento ou concluída
- Listar todas as tarefas
- Listar tarefas filtradas por status (`todo`, `in-progress`, `done`)
- Armazenamento em arquivo JSON, criado automaticamente se não existir

## Pré-requisitos

- JDK (Java Development Kit) instalado
- Nenhuma dependência ou biblioteca externa é necessária

## Como compilar

Na raiz do projeto:

```bash
javac Main.java Task.java JsonUtil.java TaskManager.java
```

## Como usar

Todos os comandos seguem o padrão:

```bash
java Main <comando> [argumentos]
```

### Adicionar uma tarefa

```bash
java Main add "Buy groceries"
# Task added successfully (ID: 1)
```

### Atualizar uma tarefa

```bash
java Main update 1 "Buy groceries and cook dinner"
```

### Remover uma tarefa

```bash
java Main delete 1
```

### Marcar como em andamento / concluída

```bash
java Main mark-in-progress 1
java Main mark-done 1
```

### Listar tarefas

```bash
java Main list                # todas as tarefas
java Main list todo           # apenas pendentes
java Main list in-progress    # apenas em andamento
java Main list done           # apenas concluídas
```

## Estrutura de uma tarefa

Cada tarefa armazenada em `tasks.json` possui:

| Campo         | Tipo   | Descrição                                  |
|---------------|--------|---------------------------------------------|
| `id`          | number | Identificador único, nunca reutilizado      |
| `description` | string | Descrição da tarefa                         |
| `status`      | string | `todo`, `in-progress` ou `done`             |
| `createdAt`   | string | Data/hora de criação                        |
| `updatedAt`   | string | Data/hora da última atualização             |

Exemplo de `tasks.json`:

```json
[
  {"id":1,"description":"Buy groceries","status":"todo","createdAt":"2026-07-12T10:00:00","updatedAt":"2026-07-12T10:00:00"}
]
```

## Arquitetura

O projeto é dividido em 4 classes, cada uma com uma responsabilidade única:

```
Main.java          → Parsing dos argumentos de linha de comando (args[]) e roteamento para o comando correto
TaskManager.java   → Regras de negócio: adicionar, atualizar, remover, marcar status e listar tarefas
JsonUtil.java       → Serialização e parsing manual de JSON (sem bibliotecas externas)
Task.java           → Modelo de dados de uma tarefa
```

### Fluxo de uma execução

1. `Main` recebe os argumentos do terminal e identifica o comando (`add`, `list`, etc.)
2. `TaskManager` é instanciado e carrega as tarefas existentes do `tasks.json` via `JsonUtil`
3. O método correspondente ao comando é executado, alterando a lista de tarefas em memória
4. `TaskManager` salva a lista atualizada de volta no `tasks.json` via `JsonUtil`

### Sobre o parsing de JSON manual

Como o projeto proíbe bibliotecas externas, a conversão entre objetos `Task` e texto JSON foi implementada manualmente em `JsonUtil`, usando `String.indexOf()` e `String.substring()` para localizar e extrair os valores de cada campo. Isso inclui:

- `taskToJson` / `listToJson`: conversão de objeto(s) para texto JSON
- `jsonToTask` / `jsonToList`: parsing de texto JSON de volta para objeto(s)
- `saveTasks` / `loadTasks`: persistência em disco usando `java.nio.file.Files`

## Tratamento de erros

- Comando inexistente ou ausente
- Argumentos faltando (ex: `add` sem descrição)
- ID inválido (não numérico) ao atualizar, remover ou marcar status
- ID inexistente na base de tarefas
- Arquivo `tasks.json` inexistente na primeira execução (criado automaticamente)

## Licença

Projeto de estudo, livre para uso e modificação.
