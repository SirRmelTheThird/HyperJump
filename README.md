## Design Patterns

### Hexagonal Architecture (Ports & Adapters)

```mermaid
classDiagram
    class StartGameUseCase {
        <<interface>>
        +play()
    }
    class DiceShaker {
        <<interface>>
        +roll() DiceRoll
        +reset()
    }
    class Board {
        <<interface>>
        +createBoard(playerCount) BoardFactory
    }
    class PathFactory {
        <<interface>>
        +createPath(board, start, end) Path
    }
    class GameSessionUseCase {
        +play()
    }
    class RandomDiceShakerAdapter {
        +roll() DiceRoll
    }
    class FixedDiceShakerAdapter {
        +roll() DiceRoll
        +reset()
    }
    class BoardFactoryAdapter {
        +createBoard(playerCount) BoardFactory
    }
    class BoardPathFactoryAdapter {
        +createPath(board, start, end) Path
    }
    class GameConsoleRunner {
        +run(args)
    }

    StartGameUseCase <|.. GameSessionUseCase : implements
    GameConsoleRunner ..> StartGameUseCase : uses

    DiceShaker <|.. RandomDiceShakerAdapter : implements
    DiceShaker <|.. FixedDiceShakerAdapter : implements
    GameSessionUseCase ..> DiceShaker : uses

    Board <|.. BoardFactoryAdapter : implements
    GameSessionUseCase ..> Board : uses

    PathFactory <|.. BoardPathFactoryAdapter : implements
    GameSessionUseCase ..> PathFactory : uses
```

The core game logic has no knowledge of how it is driven or what external services it depends on. Inbound ports such as `StartGameUseCase` define what actions can be triggered, and outbound ports such as `DiceShaker`, `Board`, and `PathFactory` define what the application needs from the outside world. `GameConsoleRunner` drives the application by using the inbound port, while concrete adapters in the infrastructure layer implement the outbound ports. Swapping the console for a UI, or a real dice shaker for a fixed test one, requires no changes to the domain.

---

### Factory Pattern

```mermaid
classDiagram
    class Board {
        <<interface>>
        +createBoard(playerCount) BoardFactory
    }
    class BoardFactory {
        <<interface>>
        +getSize() int
        +getPositions() List~Position~
    }
    class BoardFactoryAdapter {
        +createBoard(playerCount) BoardFactory
    }
    class AbstractBoard {
        <<abstract>>
        +getPositions() List~Position~
        +indexOf(position) int
        +getPosition(index) Position
    }
    class SmallBoard {
        +getSize() int
        +getCols() int
    }
    class LargeBoard {
        +getSize() int
        +getCols() int
    }

    Board <|.. BoardFactoryAdapter : implements
    BoardFactory <|.. AbstractBoard : implements
    AbstractBoard <|-- SmallBoard : extends
    AbstractBoard <|-- LargeBoard : extends
    BoardFactoryAdapter ..> SmallBoard : instantiates
    BoardFactoryAdapter ..> LargeBoard : instantiates
```

`BoardFactoryAdapter` implements the `Board` port and acts as a factory, selecting either `SmallBoard` or `LargeBoard` based on the player count passed in. Both board types extend `AbstractBoard`, which provides the shared position list logic, with each subclass supplying its own grid layout and dimensions.

---

### Decorator Pattern

```mermaid
classDiagram
    class Movement {
        <<interface>>
        +move(player, roll) TurnOutcome
    }
    class MovementDecorator {
        <<abstract>>
        #wrapped Movement
        +move(player, roll) TurnOutcome
    }
    class BasicMovement {
        +move(player, roll) TurnOutcome
    }
    class TeleportVariationDecorator {
        +move(player, roll) TurnOutcome
    }
    class ExactEndVariationDecorator {
        +move(player, roll) TurnOutcome
    }
    class HitVariationDecorator {
        +move(player, roll) TurnOutcome
    }

    Movement <|.. BasicMovement : implements
    Movement <|.. MovementDecorator : implements
    Movement <--o MovementDecorator : uses
    MovementDecorator <|-- TeleportVariationDecorator : extends
    MovementDecorator <|-- ExactEndVariationDecorator : extends
    MovementDecorator <|-- HitVariationDecorator : extends
```

`BasicMovement` handles a standard move by advancing the player along their path by the dice roll total. Each active game rule wraps this in a decorator — `TeleportVariationDecorator`, `ExactEndVariationDecorator`, and `HitVariationDecorator` — that adds its own behaviour around the delegate call. This allows any combination of rules to be layered onto the movement pipeline at runtime without modifying existing classes.

---

### Strategy Pattern

```mermaid
classDiagram
    class RuleSelectionStrategy {
        <<interface>>
        +select(availableRules) List~GameRule~
    }
    class RandomRuleSelection {
        +select(availableRules) List~GameRule~
    }
    class FixedRuleSelection {
        +select(availableRules) List~GameRule~
    }
    class MovementDecoratorStrategy {
        <<interface>>
        +supports(rule) boolean
        +decorate(movement, rule) Movement
    }
    class TeleportVariation {
        +supports(rule) boolean
        +decorate(movement, rule) Movement
    }
    class ExactEndVariation {
        +supports(rule) boolean
        +decorate(movement, rule) Movement
    }
    class HitVariation {
        +supports(rule) boolean
        +decorate(movement, rule) Movement
    }
    class PlayerSelector {
        <<interface>>
        +getCurrentPlayer() Player
        +next()
        +hasTakenATurn()
    }
    class RoundRobinPlayerSelector {
        +getCurrentPlayer() Player
        +next()
        +hasTakenATurn()
    }

    RuleSelectionStrategy <|.. RandomRuleSelection : implements
    RuleSelectionStrategy <|.. FixedRuleSelection : implements
    MovementDecoratorStrategy <|.. TeleportVariation : implements
    MovementDecoratorStrategy <|.. ExactEndVariation : implements
    MovementDecoratorStrategy <|.. HitVariation : implements
    PlayerSelector <|.. RoundRobinPlayerSelector : implements
```

Three separate strategy hierarchies are used. `RuleSelectionStrategy` controls which rules are active for a session — `RandomRuleSelection` picks a random subset while `FixedRuleSelection` uses a predetermined list. `MovementDecoratorStrategy` controls how each rule is applied to the movement pipeline, with each implementation knowing which rule it supports and how to decorate it. `PlayerSelector` controls turn order, with `RoundRobinPlayerSelector` cycling through players in sequence.

---

### State Pattern

```mermaid
classDiagram
    class GameState {
        <<interface>>
        +handle(player, playerTurn)
        +isGameOver() boolean
        +nextState() GameState
    }
    class InPlayState {
        +handle(player, playerTurn)
        +isGameOver() boolean
        +nextState() GameState
    }
    class GameOverState {
        +handle(player, playerTurn)
        +isGameOver() boolean
        +getWinner() Player
    }

    GameState <|.. InPlayState : implements
    GameState <|.. GameOverState : implements
    InPlayState ..> GameOverState : instantiates
```

The game progresses through two states: `InPlayState` and `GameOverState`, both implementing the `GameState` interface. After each turn `InPlayState` checks whether the current player has reached the end of their path, and if so instantiates a `GameOverState` and returns it from `nextState()`. The game loop in `PlayerTurn` simply calls `isGameOver()` each round with no conditional logic about which state it is in.

---

### Observer Pattern

```mermaid
classDiagram
    class PlayerTurnObserverPort {
        <<interface>>
        +onTurnPlayed(playerTurn)
    }
    class GameOverObserverPort {
        <<interface>>
        +onGameOver(playerTurn)
    }
    class GameStartObserverPort {
        <<interface>>
        +onGameStarted(players)
    }
    class PlayerTurn {
        +play()
        +addObserver(observer)
        +addGameOverObserver(observer)
    }
    class GameSessionUseCase {
        +addObserver(observer)
    }
    class TurnDisplayAdapter {
        +onTurnPlayed(playerTurn)
    }
    class GameOverDisplayAdapter {
        +onGameOver(playerTurn)
    }
    class PathsDisplayAdapter {
        +onGameStarted(players)
    }

    PlayerTurnObserverPort <|.. TurnDisplayAdapter : implements
    GameOverObserverPort <|.. GameOverDisplayAdapter : implements
    GameStartObserverPort <|.. PathsDisplayAdapter : implements
    PlayerTurn ..> PlayerTurnObserverPort : uses
    PlayerTurn ..> GameOverObserverPort : uses
    GameSessionUseCase ..> GameStartObserverPort : uses
```

Three observer ports — `PlayerTurnObserverPort`, `GameOverObserverPort`, and `GameStartObserverPort` — allow the domain to broadcast events without knowing who is listening. The infrastructure display adapters register themselves and react by printing output to the console. Adding a new output target such as a file logger or a UI requires only a new adapter implementing the relevant port, with no changes to the domain.

---

### Template Method Pattern

```mermaid
classDiagram
    class AbstractDiceShaker {
        <<abstract>>
        #singleDie() int
        +roll() DiceRoll
    }
    class RandomSingleDiceShaker {
        +toArray() int[]
    }
    class RandomDoubleDiceShaker {
        +toArray() int[]
    }
    class FixedSingleDiceShaker {
        +toArray() int[]
        +reset()
    }
    class Path {
        <<interface>>
        +getPositions() List~Position~
    }
    class AbstractPath {
        <<abstract>>
        +getPositions() List~Position~
    }
    class ForwardRowPath {
        +getPositions() List~Position~
    }
    class BackwardRowPath {
        +getPositions() List~Position~
    }
    class RotatedPath {
        +getPositions() List~Position~
    }
    class ReversedRotatedPath {
        +getPositions() List~Position~
    }

    AbstractDiceShaker <|-- RandomSingleDiceShaker : extends
    AbstractDiceShaker <|-- RandomDoubleDiceShaker : extends
    AbstractDiceShaker <|-- FixedSingleDiceShaker : extends
    Path <|.. AbstractPath : implements
    AbstractPath <|-- ForwardRowPath : extends
    AbstractPath <|-- BackwardRowPath : extends
    AbstractPath <|-- RotatedPath : extends
    AbstractPath <|-- ReversedRotatedPath : extends
```

Two Template Method hierarchies exist. `AbstractDiceShaker` defines the algorithm for rolling — calling `toArray()` and wrapping the result in a `DiceRoll` — leaving the die values to each subclass. `AbstractPath` similarly defines path storage and retrieval, with each subclass providing a differently oriented sequence of positions on the board. The shared logic is written once in the abstract class and never repeated.