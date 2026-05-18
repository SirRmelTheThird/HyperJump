
## Application Startup Flow

This diagram represents the entry point and application flow of the game using a Clean Architecture approach.

```mermaid
classDiagram
    direction LR

    class GameConsoleRunner {
    }

    class CommandLineRunner {
        <<interface>>
        +run(String... args)
    }

    class StartGameUseCase {
        <<interface>>
        +play()
    }

    class StartGameService {
        +play()
    }

    class GameSessionUseCase {
        +setupGame()
        +startGame()
    }

    GameConsoleRunner ..|> CommandLineRunner
    GameConsoleRunner ..> StartGameUseCase : << use >>

    StartGameService ..|> StartGameUseCase
    StartGameService ..> GameSessionUseCase : << use >>
```

### Components

#### GameConsoleRunner
- Acts as the application's entry point.
- Implements Spring Boot’s `CommandLineRunner`.
- Executes automatically when the application starts.

#### CommandLineRunner
- Spring Boot interface used to run console-based applications.

#### StartGameUseCase
- Input port of the application.
- Defines the behaviour exposed to external layers through:

```text
play()
```

#### StartGameService
- Concrete implementation of `StartGameUseCase`.
- Responsible for orchestrating the game startup process.

#### GameSessionUseCase
- Coordinates the core gameplay lifecycle.
- Handles:
    - `setupGame()`
    - `startGame()`

### Execution Flow

```text
Spring Boot Application
        ↓
GameConsoleRunner.run()
        ↓
StartGameUseCase.play()
        ↓
StartGameService
        ↓
GameSessionUseCase
        ↓
Game setup and gameplay execution
```

### Architectural Concepts Demonstrated

- Clean Architecture
- Dependency Inversion Principle
- Input Port Pattern
- Use Case Orchestration
- Separation of Concerns
- Framework-independent business logic


## Board Hierarchy

```mermaid
classDiagram
    AbstractBoard <|-- SmallBoard
    AbstractBoard <|-- LargeBoard
    BoardFactory <|.. AbstractBoard

    class BoardFactory {
        <<interface>>
    }

    class AbstractBoard {
        +getPositions(): List~Position~
        +getCols(): int
    }

    class SmallBoard
    class LargeBoard
```

This diagram represents the board creation hierarchy used within the game.

### Structure

- `BoardFactory`
    - Defines the common contract for all board implementations.
    - Acts as the abstraction used throughout the application layer.

- `AbstractBoard`
    - Provides shared board behaviour and reusable logic.
    - Contains common functionality such as:
        - retrieving board positions
        - retrieving column counts

- `SmallBoard`
    - Concrete implementation for smaller game configurations.

- `LargeBoard`
    - Concrete implementation for larger game configurations.

### Architectural Concepts

- Interface-based abstraction
- Template Method / Abstract Base Class pattern
- Shared domain behaviour reuse
- Polymorphic board implementations

---

## Dice Hierarchy

```mermaid
classDiagram
    AbstractDiceShaker <|-- RandomSingleDiceShaker
    AbstractDiceShaker <|-- RandomDoubleDiceShaker
    DiceShakerFactory <|.. AbstractDiceShaker
    DiceShakerFactory <|.. FixedSingleDiceShaker

    class DiceShakerFactory {
        <<interface>>
        +roll(): DiceRoll
    }

    class AbstractDiceShaker {
        +roll(): DiceRoll
    }

    class RandomSingleDiceShaker
    class RandomDoubleDiceShaker

    class FixedSingleDiceShaker {
        +reset(): void
    }
```

This diagram represents the dice rolling hierarchy used for gameplay and testing.

### Structure

- `DiceShakerFactory`
    - Defines the dice rolling contract used by the application.

- `AbstractDiceShaker`
    - Provides shared dice rolling behaviour for random dice implementations.

- `RandomSingleDiceShaker`
    - Simulates rolling a single random dice.

- `RandomDoubleDiceShaker`
    - Simulates rolling two random dice.

- `FixedSingleDiceShaker`
    - Deterministic dice implementation used for testing and replayable game states.
    - Supports resetting the predefined sequence.

### Architectural Concepts

- Strategy pattern
- Template Method / Abstract Base Class pattern
- Testable deterministic implementations
- Runtime-swappable behaviour

---

## Path Hierarchy

```mermaid
classDiagram
    AbstractPath <|-- ForwardRowPath
    AbstractPath <|-- BackwardRowPath
    AbstractPath <|-- RotatedPath
    AbstractPath <|-- ReversedRotatedPath
    PathFactory <|.. AbstractPath

    class PathFactory {
        <<interface>>
        +getPositions(): List~Position~
    }

    class AbstractPath {
        +getPositions(): List~Position~
    }

    class ForwardRowPath
    class BackwardRowPath
    class RotatedPath
    class ReversedRotatedPath
```

This diagram represents the player path hierarchy used to generate movement routes across the board.

### Structure

- `PathFactory`
    - Defines the common contract for all path implementations.

- `AbstractPath`
    - Provides shared functionality for storing and retrieving path positions.

- `ForwardRowPath`
    - Generates paths moving forward row by row.

- `BackwardRowPath`
    - Generates paths moving backward across rows.

- `RotatedPath`
    - Generates rotated traversal paths across the board.

- `ReversedRotatedPath`
    - Generates reversed rotated traversal paths.

### Architectural Concepts

- Strategy-based path generation
- Polymorphic movement paths
- Shared path behaviour reuse
- Encapsulation of traversal algorithms


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