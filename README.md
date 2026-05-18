```mermaid
classDiagram
direction LR

class GameConsoleRunner
class CommandLineRunner {
    <<interface>>
    +run(String... args)
}

class StartGameUseCase {
    <<interface>>
    +play()
}

GameConsoleRunner ..|> CommandLineRunner
GameConsoleRunner ..> StartGameUseCase : << use >>
```
fdsfdsafdafdsfd
fdsfafdsfa

```mermaid
classDiagram
direction TB

class GameSessionUseCase
class InitialisePlayerUseCase
class InitialiseRulesUseCase

class Board {
    <<interface>>
}

class PathFactory {
    <<interface>>
}

class DiceShaker {
    <<interface>>
}

GameSessionUseCase --> InitialisePlayerUseCase
GameSessionUseCase --> InitialiseRulesUseCase

GameSessionUseCase ..> Board
GameSessionUseCase ..> PathFactory

InitialisePlayerUseCase ..> DiceShaker
```
fdsafdsfds
fdafssdfdasaf


```mermaid
classDiagram
direction LR

class DiceShaker {
    <<interface>>
}

class Board {
    <<interface>>
}

class PathFactory {
    <<interface>>
}

class RandomDiceShakerAdapter
class FixedDiceShakerAdapter
class BoardFactoryAdapter
class BoardPathFactoryAdapter

RandomDiceShakerAdapter ..|> DiceShaker
FixedDiceShakerAdapter ..|> DiceShaker

BoardFactoryAdapter ..|> Board
BoardPathFactoryAdapter ..|> PathFactory
```

```mermaid
classDiagram
Board <|.. BoardFactoryAdapter
BoardFactoryAdapter --> SmallBoard : instantiate
BoardFactoryAdapter --> LargeBoard : instantiate
SmallBoard --|> BoardFactory
LargeBoard --|> BoardFactory

    class Board {
        <<interface>>
        createBoard(playerCount): BoardFactory
    }

    class BoardFactoryAdapter {
        createBoard(playerCount): BoardFactory
    }

    class BoardFactory {
        <<interface>>
    }

    class SmallBoard
    class LargeBoard
```

Board Factory Stuff

```mermaid
classDiagram
RuleSelectionStrategy <|.. RandomRuleSelection
RuleSelectionStrategy <|.. FixedRuleSelection
InitialiseRulesUseCase --> RuleSelectionStrategy : uses

    class RuleSelectionStrategy {
        <<interface>>
        select(availableRules): List~GameRule~
    }

    class RandomRuleSelection {
        select(availableRules): List~GameRule~
    }

    class FixedRuleSelection {
        select(availableRules): List~GameRule~
    }

    class InitialiseRulesUseCase {
        setupRules(boardSize, players): List~GameRule~
    }
```

```mermaid
classDiagram
TeleportGenerationStrategy <|.. RandomTeleportGeneration
TeleportGenerationStrategy <|.. FixedTeleportGeneration
TeleportRule --> TeleportGenerationStrategy : uses

    class TeleportGenerationStrategy {
        <<interface>>
        generate(boardSize, players): Map~Position, Position~
    }

    class RandomTeleportGeneration {
        generate(boardSize, players): Map~Position, Position~
    }

    class FixedTeleportGeneration {
        generate(boardSize, players): Map~Position, Position~
    }

    class TeleportRule
```

```mermaid
classDiagram
GameEvent <|.. TeleportEvent
GameEvent <|.. HitEvent
GameEvent <|.. ExactEndEvent
TurnOutcome --> GameEvent : contains

    class GameEvent {
        <<interface>>
        describe(player): String
    }

    class TeleportEvent {
        describe(player): String
    }

    class HitEvent {
        describe(player): String
    }

    class ExactEndEvent {
        describe(player): String
    }

    class TurnOutcome {
        addEvent(event): void
        getEvents(): List~GameEvent~
    }
```

```mermaid
classDiagram
MovementDecoratorStrategy <|.. TeleportVariation
MovementDecoratorStrategy <|.. ExactEndVariation
MovementDecoratorStrategy <|.. HitVariation
TeleportVariation --> TeleportVariationDecorator : instantiate
ExactEndVariation --> ExactEndVariationDecorator : instantiate
HitVariation --> HitVariationDecorator : instantiate

    class MovementDecoratorStrategy {
        <<interface>>
        supports(rule): boolean
        decorate(movement, rule): Movement
    }

    class TeleportVariation {
        supports(rule): boolean
        decorate(movement, rule): Movement
    }

    class ExactEndVariation {
        supports(rule): boolean
        decorate(movement, rule): Movement
    }

    class HitVariation {
        supports(rule): boolean
        decorate(movement, rule): Movement
    }

```

Strategy Stuff

```mermaid
classDiagram
GameState <|.. InPlayState
GameState <|.. GameOverState
InPlayState --> GameOverState : transition

    class GameState {
        <<interface>>
        handle(currentPlayer, playerTurn): void
        isGameOver(): boolean
        nextState(): GameState
    }

    class InPlayState {
        handle(currentPlayer, playerTurn): void
        isGameOver(): boolean
        nextState(): GameState
    }

    class GameOverState {
        -winner: Player
        handle(currentPlayer, playerTurn): void
        isGameOver(): boolean
        nextState(): GameState
        getWinner(): Player
    }

```

State stuff


```mermaid
classDiagram
PlayerTurnObserverPort <|.. TurnDisplayAdapter
GameOverObserverPort <|.. GameOverDisplayAdapter
GameStartObserverPort <|.. PathsDisplayAdapter
GameRulesObserverPort <|.. RulesDisplayAdapter
PlayerTurn --> PlayerTurnObserverPort : notifies
PlayerTurn --> GameOverObserverPort : notifies
GameSessionUseCase --> GameStartObserverPort : notifies
GameSessionUseCase --> GameRulesObserverPort : notifies

    class PlayerTurnObserverPort {
        <<interface>>
        onTurnPlayed(playerTurn): void
    }

    class GameOverObserverPort {
        <<interface>>
        onGameOver(playerTurn): void
    }

    class GameStartObserverPort {
        <<interface>>
        onGameStarted(players): void
    }

    class GameRulesObserverPort {
        <<interface>>
        onRulesSelected(rules): void
    }

    class TurnDisplayAdapter
    class GameOverDisplayAdapter
    class PathsDisplayAdapter
    class RulesDisplayAdapter
```

Observer Stuff

```mermaid
classDiagram
Movement <|.. BasicMovement
Movement <|.. MovementDecorator
MovementDecorator <|-- TeleportVariationDecorator
MovementDecorator <|-- ExactEndVariationDecorator
MovementDecorator <|-- HitVariationDecorator
MovementDecorator --> Movement : wraps

    class Movement {
        <<interface>>
        move(player, roll): TurnOutcome
    }

    class BasicMovement {
        move(player, roll): TurnOutcome
    }

    class MovementDecorator {
        -wrapped: Movement
        move(player, roll): TurnOutcome
    }

    class TeleportVariationDecorator
    class ExactEndVariationDecorator
    class HitVariationDecorator
```

```mermaid
classDiagram
direction LR

    namespace Adapter {
        Board <|.. BoardFactoryAdapter
        BoardFactoryAdapter --> SmallBoard : instantiate
        BoardFactoryAdapter --> LargeBoard : instantiate
        SmallBoard --|> BoardFactory
        LargeBoard --|> BoardFactory

        class Board {
            <<interface>>
            createBoard(playerCount): BoardFactory
        }

        class BoardFactoryAdapter {
            createBoard(playerCount): BoardFactory
        }

        class BoardFactory {
            <<interface>>
        }

        class SmallBoard
        class LargeBoard
    }

    namespace TemplateMethod {
        BoardFactory <|.. AbstractBoard
        AbstractBoard <|-- SmallBoard
        AbstractBoard <|-- LargeBoard

        class AbstractBoard {
            #buildBoard(): List~Position~
            +getPositions(): List~Position~
            +getCols(): int
        }
    }
```