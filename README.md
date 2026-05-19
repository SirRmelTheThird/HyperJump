# HyperJump
Introduction Here

# Architecture Evaluation

The architecture of HyperJump follows a modular and loosely coupled structure that separates the application into multiple layers and responsibilities. The system is heavily influenced by Hexagonal Architecture, where communication occurs through interfaces (ports) and concrete implementations (adapters).

### Game
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
        + play()
    }

    class StartGameService {
        + play()
    }

    class GameSessionUseCase {
        + setupGame()
        + startGame()
    }

    GameConsoleRunner ..|> CommandLineRunner
    GameConsoleRunner ..> StartGameUseCase : << use >>

    StartGameService ..|> StartGameUseCase
    StartGameService ..> GameSessionUseCase : << use >>
```

## Strengths of the Architecture

### Separation of Concerns

The project cleanly separates:
- Application logic
- Domain/game logic
- Infrastructure implementations
- User interaction

This improves readability and maintainability because each component has a clearly defined responsibility.

### Loose Coupling Through Interfaces

Interfaces such as:
- `StartGameUseCase`
- `SavedGameRepository`
- Observer ports
- Board
- DiceShaker
- Rule

allow the core game logic to remain independent from implementation details. This improves flexibility and testability.

### Extensibility

The architecture allows additional features to be added without modifying existing core logic. Examples include:
- Adding new board types
- Introducing new movement rules
- Supporting alternative storage systems
- Creating graphical interfaces instead of console adapters

### Testability

Because dependencies are injected through abstractions, components can be mocked or replaced during testing. This improves unit testing capabilities and reduces dependency on infrastructure implementations.

### Replay and Persistence Support
```mermaid
classDiagram
    direction LR

    class GameConsoleRunner {
    }

    class CommandLineRunner {
        <<interface>>
        +run(String... args)
    }

    class ReplayGameUseCase {
        <<interface>>
        + replay()
    }

    class ReplayGameService {
        + replay()
    }

    class ReplaySessionUseCase {
        + setupReplay()
        + startReplay()
    }

    GameConsoleRunner ..|> CommandLineRunner
    GameConsoleRunner ..> ReplayGameUseCase : << use >>

    ReplayGameService ..|> ReplayGameUseCase
    ReplayGameService ..> ReplaySessionUseCase : << use >>
```
The replay subsystem demonstrates strong architectural separation by isolating replay functionality from standard gameplay. Repository abstractions also support multiple persistence strategies.

---

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

## Board Adapter
This diagram represents the board creation and board hierarchy used within the game.

```mermaid
classDiagram
Board <|.. BoardFactoryAdapter
BoardFactoryAdapter --> SmallBoard : instantiate
BoardFactoryAdapter --> LargeBoard : instantiate

    SmallBoard --|> AbstractBoard
    LargeBoard --|> AbstractBoard

    AbstractBoard --|> BoardFactory

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

    class AbstractBoard {
        + getPositions(): List~Position~
    }

    class SmallBoard 
    
    class LargeBoard 
```

- `Board` acts as the output port used by the application layer.
- `BoardFactoryAdapter` is the driven adapter responsible for creating the correct board implementation.
- `SmallBoard` and `LargeBoard` are concrete board implementations.
- `AbstractBoard` contains shared board behaviour such as:
    - board generation
    - retrieving positions
    - retrieving column counts
- `BoardFactory` defines the common abstraction for all board implementations.

### Architectural Concepts

- Ports and Adapters Architecture
- Adapter Pattern
- Factory Pattern
- Template Method / Abstract Base Class pattern
- Polymorphism
- Shared domain behaviour reuse

## DiceShaker Adapter 
This diagram represents the dice rolling system used for gameplay and testing.

```mermaid
classDiagram
DiceShaker <|.. RandomDiceShakerAdapter
DiceShaker <|.. FixedDiceShakerAdapter

    RandomDiceShakerAdapter --> RandomSingleDiceShaker : uses
    RandomDiceShakerAdapter --> RandomDoubleDiceShaker : uses
    FixedDiceShakerAdapter --> FixedSingleDiceShaker : uses

    RandomSingleDiceShaker --|> AbstractDiceShaker
    RandomDoubleDiceShaker --|> AbstractDiceShaker

    AbstractDiceShaker --|> DiceShakerFactory

    FixedSingleDiceShaker ..|> DiceShakerFactory

    class DiceShaker {
        <<interface>>
        roll(): DiceRoll
    }

    class RandomDiceShakerAdapter {
        roll(): DiceRoll
    }

    class FixedDiceShakerAdapter {
        roll(): DiceRoll
        reset(): void
    }

    class DiceShakerFactory {
        <<interface>>
        roll(): DiceRoll
    }

    class AbstractDiceShaker {
        + toArray(): int[]
        + roll(): DiceRoll
    }

    class RandomSingleDiceShaker

    class RandomDoubleDiceShaker

    class FixedSingleDiceShaker {
        + reset(): void
    }
```

- `DiceShaker` acts as the output port used by the application layer.
- `RandomDiceShakerAdapter` provides random gameplay behaviour.
- `FixedDiceShakerAdapter` provides deterministic behaviour for testing and replayable game states.
- `RandomSingleDiceShaker` and `RandomDoubleDiceShaker` inherit shared rolling logic from `AbstractDiceShaker`.
- `FixedSingleDiceShaker` implements fixed deterministic rolling behaviour independently.

### Architectural Concepts

- Ports and Adapters Architecture
- Adapter Pattern
- Strategy Pattern
- Template Method / Abstract Base Class pattern
- Runtime-swappable behaviour
- Deterministic testing support

## Path Adapter
This diagram represents the path generation system used to create player traversal routes across the board.

```mermaid
classDiagram
Path <|.. BoardPathFactoryAdapter

    BoardPathFactoryAdapter --> ForwardRowPath : instantiate
    BoardPathFactoryAdapter --> BackwardRowPath : instantiate
    BoardPathFactoryAdapter --> RotatedPath : instantiate
    BoardPathFactoryAdapter --> ReversedRotatedPath : instantiate

    ForwardRowPath --|> AbstractPath
    BackwardRowPath --|> AbstractPath
    RotatedPath --|> AbstractPath
    ReversedRotatedPath --|> AbstractPath

    AbstractPath --|> PathFactory

    class Path {
        <<interface>>
        createPath(board, start, end): Path
    }

    class BoardPathFactoryAdapter {
        createPath(board, start, end): Path
    }

    class PathFactory {
        <<interface>>
        getPositions(): List~Position~
    }

    class AbstractPath {
        + getPositions(): List~Position~
    }

    class ForwardRowPath

    class BackwardRowPath

    class RotatedPath

    class ReversedRotatedPath
```

- `Path` acts as the output port used by the application layer.
- `BoardPathFactoryAdapter` determines and creates the correct path implementation.
- `ForwardRowPath` generates forward row traversal paths.
- `BackwardRowPath` generates backward traversal paths.
- `RotatedPath` generates rotated traversal paths.
- `ReversedRotatedPath` generates reversed rotated traversal paths.
- `AbstractPath` provides shared path behaviour and position storage functionality.
- `PathFactory` defines the common abstraction for all path implementations.

### Architectural Concepts

- Ports and Adapters Architecture
- Adapter Pattern
- Factory Pattern
- Strategy-based path generation
- Template Method / Abstract Base Class pattern
- Encapsulation of traversal algorithms

## Board Factory Pattern
This diagram represents the board creation hierarchy used within the game.

```mermaid
classDiagram
    AbstractBoard <|-- SmallBoard
    AbstractBoard <|-- LargeBoard
    BoardFactory <|.. AbstractBoard

    class BoardFactory {
        <<interface>>
    }

    class AbstractBoard {
        + getPositions(): List~Position~
    }

    class SmallBoard

    class LargeBoard

```

### Structure

- `BoardFactory`
    - Defines the common contract for all board implementations.
    - Acts as the abstraction used throughout the application layer.

- `AbstractBoard`
    - Provides shared board behaviour and reusable logic.
    - Contains common functionality such as:
        - retrieving board positions
        - retrieving column counts

- `SmallBoard` Concrete implementation for smaller game configurations.
- `LargeBoard` Concrete implementation for larger game configurations.

### Architectural Concepts

- Interface-based abstraction
- Template Method / Abstract Base Class pattern
- Shared domain behaviour reuse
- Polymorphic board implementations

## Dice Factory Pattern
This diagram represents the player path hierarchy used to generate movement routes across the board.

```mermaid
classDiagram
    AbstractDiceShaker <|-- RandomSingleDiceShaker
    AbstractDiceShaker <|-- RandomDoubleDiceShaker
    DiceShakerFactory <|.. AbstractDiceShaker
    DiceShakerFactory <|.. FixedSingleDiceShaker

    class DiceShakerFactory {
        <<interface>>
        + roll(): DiceRoll
    }

    class AbstractDiceShaker {
        + roll(): DiceRoll
    }

    class RandomSingleDiceShaker
    class RandomDoubleDiceShaker

    class FixedSingleDiceShaker {
        + reset(): void
    }
```

### Structure

- `DiceShakerFactory` Defines the dice rolling contract used by the application.
- `AbstractDiceShaker` Provides shared dice rolling behaviour for random dice implementations.
- `RandomSingleDiceShaker` Simulates rolling a single random dice.
- `RandomDoubleDiceShaker` Simulates rolling two random dice.
- `FixedSingleDiceShaker`
    - Deterministic dice implementation used for testing and replayable game states.
    - Supports resetting the predefined sequence.

### Architectural Concepts

- Strategy pattern
- Template Method / Abstract Base Class pattern
- Testable deterministic implementations
- Runtime-swappable behaviour

## Path Factory Pattern
This diagram represents the player path hierarchy used to generate movement routes across the board.

```mermaid
classDiagram
    AbstractPath <|-- ForwardRowPath
    AbstractPath <|-- BackwardRowPath
    AbstractPath <|-- RotatedPath
    AbstractPath <|-- ReversedRotatedPath
    PathFactory <|.. AbstractPath

    class PathFactory {
        <<interface>>
        + getPositions(): List~Position~
    }

    class AbstractPath {
        + getPositions(): List~Position~
    }

    class ForwardRowPath
    class BackwardRowPath
    class RotatedPath
    class ReversedRotatedPath
```

### Structure

- `PathFactory` Defines the common contract for all path implementations.
- `AbstractPath` Provides shared functionality for storing and retrieving path positions.
- `ForwardRowPath` Generates paths moving forward row by row.
- `BackwardRowPath` Generates paths moving backward across rows.
- `RotatedPath` Generates rotated traversal paths across the board.
- `ReversedRotatedPath` Generates reversed rotated traversal paths.

### Architectural Concepts

- Strategy-based path generation
- Polymorphic movement paths
- Shared path behaviour reuse
- Encapsulation of traversal algorithms


## Rule Selection Strategy
This diagram shows how the game chooses which rule variations are active.

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

- `RuleSelectionStrategy` defines the rule-selection contract.
- `RandomRuleSelection` is used for random/real gameplay.
- `FixedRuleSelection` is used for predictable testing.
- `InitialiseRulesUseCase` depends on the strategy interface, not a concrete implementation.

### Architectural Concepts

- Strategy Pattern
- Dependency Inversion
- Runtime-swappable behaviour
- Test-friendly configuration

## Teleport Strategy Pattern
This diagram shows how teleport/wormhole positions are generated.

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

- `TeleportGenerationStrategy` defines how wormholes are created.
- `RandomTeleportGeneration` creates random wormhole positions.
- `FixedTeleportGeneration` creates predictable wormholes for testing.
- `TeleportRule` uses the strategy without knowing whether it is random or fixed.

### Architectural Concepts

- Strategy Pattern
- Open/Closed Principle
- Testable rule behaviour
- Separation between rule logic and generation logic

## Events Strategy Pattern
This diagram shows how important events from a turn are stored and described.

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


- `GameEvent` defines a common interface for turn events.
- `TeleportEvent` represents a player landing on a wormhole.
- `HitEvent` represents a player hitting another player.
- `ExactEndEvent` represents a player overshooting and bouncing back.
- `TurnOutcome` stores all events that happened during a turn.

### Architectural Concepts

- Domain Event Pattern
- Polymorphism
- Encapsulation of event-specific details
- Cleaner display logic without large switch statements

## Movement Strategy Pattern
This diagram shows how selected rules are converted into movement decorators.

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

- `MovementDecoratorStrategy` defines how a rule can decorate movement.
- `TeleportVariation` creates a teleport movement decorator.
- `ExactEndVariation` creates an exact-end movement decorator.
- `HitVariation` creates a hit movement decorator.
- Each strategy checks whether it supports a rule, then wraps the movement logic if needed.

### Architectural Concepts

- Strategy Pattern
- Factory-style object creation
- Decorator Pattern support
- Open/Closed Principle

## Movement Decorator Pattern
This diagram represents the movement system and decorator hierarchy used to apply gameplay rule variations dynamically.

```mermaid
classDiagram
    direction TB

    Movement <|.. BasicMovement
    Movement <|.. MovementDecorator

    MovementDecorator --> Movement : wraps

    MovementDecorator <|-- TeleportVariationDecorator
    MovementDecorator <|-- ExactEndVariationDecorator
    MovementDecorator <|-- HitVariationDecorator

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

- `Movement` defines the core movement contract used throughout the game.
- `BasicMovement` provides the default player movement behaviour.
- `MovementDecorator` acts as the abstract decorator base class.
    - Wraps another `Movement` implementation.
    - Allows additional behaviour to be layered dynamically.
- `TeleportVariationDecorator` Adds teleport/wormhole movement behaviour.
- `ExactEndVariationDecorator` Adds exact-end win conditions and bounce-back behaviour.
- `HitVariationDecorator` Adds player hit/collision behaviour.

The decorators wrap the base movement implementation at runtime, allowing multiple rule variations to be combined without modifying the core movement logic.

### Architectural Concepts

- Decorator Pattern
- Open/Closed Principle
- Runtime composition of behaviour
- Polymorphism
- Behaviour extension without modifying existing classes
- Separation of core movement logic from rule variations

## Game State Pattern
This diagram represents the game lifecycle state system used to control gameplay flow.

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
        - winner: Player
        handle(currentPlayer, playerTurn): void
        isGameOver(): boolean
        nextState(): GameState
        getWinner(): Player
    }
```

- `GameState` defines the common contract for all game states.
    - Handles state-specific behaviour.
    - Determines whether the game has ended.
    - Controls state transitions.

- `InPlayState`
    - Represents the active gameplay state.
    - Processes turns while the game is still running.
    - Transitions to `GameOverState` once a winner is detected.

- `GameOverState`
    - Represents the completed game state.
    - Stores the winning player.
    - Prevents further gameplay execution once the game has ended.

The game transitions from `InPlayState` to `GameOverState` dynamically at runtime based on gameplay conditions.

### Architectural Concepts

- State Pattern
- Encapsulation of state-specific behaviour
- Runtime state transitions
- Separation of gameplay lifecycle logic
- Polymorphism
- Cleaner control flow without large conditional statements

## Display Observer Pattern
This diagram represents the observer system used to notify display adapters about important gameplay events.

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

- `PlayerTurnObserverPort` Defines the contract for responding to player turn events.
- `GameOverObserverPort` Defines the contract for responding to game completion events.
- `GameStartObserverPort` Defines the contract for responding to game start events.
- `GameRulesObserverPort` Defines the contract for responding to selected rule events.
- `TurnDisplayAdapter` Displays player turn information.
- `GameOverDisplayAdapter` Displays winner and game-over information.
- `PathsDisplayAdapter` Displays player movement paths when the game starts.
- `RulesDisplayAdapter` Displays the active rules selected for the game.
- `PlayerTurn` Notifies observers whenever a turn is played or the game ends.
- `GameSessionUseCase` Notifies observers when the game starts and when rules are selected.

This architecture allows display behaviour to remain separated from the core gameplay logic.

### Architectural Concepts

- Observer Pattern
- Ports and Adapters Architecture
- Event-driven communication
- Loose coupling
- Separation of Concerns
- Dependency Inversion Principle
- Extensible notification system
