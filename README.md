# Ascend

App tipo **LifeRPG**: o utilizador recebe quests do dia a dia (sair de casa, caminhar, conhecer sítios novos),
prova que as fez e ganha XP para subir de nível.

- **Mobile (este repositório, por agora):** app Android nativa em Kotlin + Jetpack Compose.
- **Web:** painel do gestor para criar e editar quests (ainda não iniciado).

Design: ficheiro Figma "Ascend".

## Âmbito da versão 1

Incluído:
- Lista de quests e detalhe da quest
- Prova por **foto** (único tipo de prova)
- XP e nível simples (1000 XP por nível)
- Perfil simples (nível, XP, quests concluídas)
- Tema claro/escuro

Fora da versão 1: outros tipos de prova (distância, etc.), ranking, conquistas, ficha de personagem,
bónus de "primeiro a completar", quests personalizadas, lista de utilizadores no web.

## Como correr

1. Instalar o [Android Studio](https://developer.android.com/studio).
2. **File > Open** e escolher esta pasta. Esperar pelo *Gradle Sync* (a primeira vez demora).
3. Escolher um emulador (ou um telemóvel com depuração USB) e carregar em **Run**.

Se o Android Studio sugerir atualizar o Gradle/AGP, aceitar.

## Estrutura

```
app/src/main/java/com/rockabits/ascend/
  MainActivity.kt
  data/     Quest, QuestRepository (quests locais de exemplo), ProgressStore (XP guardado no telemóvel)
  ui/       AscendApp (navegação), AscendViewModel (lógica de XP e nível)
    theme/        cores e tipos de letra do Figma
    components/   barra de navegação, botões, barras de progresso
    screens/      Início, Quests, Detalhe da quest, Perfil, Definições
```

## Limitações conhecidas

- As quests são fixas no código. Mais tarde virão de um backend (gerido pelo painel web).
- A foto de prova **não é guardada nem enviada**, só aparece no ecrã. Falta decidir como validar
  que a foto é verdadeira (questão em aberto no A02).
- Sem contas de utilizador: o progresso fica só no telemóvel.
- As texturas de pontos (halftone) dos cartões e o menu lateral do Figma ainda não foram implementados.
