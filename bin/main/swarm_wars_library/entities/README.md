**entities**

Contains concrete implementation of 
* `Bot`
* `Bullet`
* `PlayerAI`
* `PlayerN`
* `Turret`

The above classes all inherit from the abstract class,
* `Abstract Entity`
which itself implements the interfaces, (known as a skeletal implementation of)
* `IEntity`
* `IComms`

However it leaves two abstract methods to be implemented
* `update()`
* `collidedWith()`

We then extend the behaviour of Abstract Entity by using mixin classes. For 
example, PlayerAI implements
* `IHealth`
* `IAIMovement`
* `IAIShooter`
* `IScore`
These mixin interfaces do not include any concrete implementation but form a 
contract on expected behaviour for the class.

We then finally get the concrete implementation by using composition. Again, 
the rule is that PlayerAI 'is an' Entity (AbstractEntity) should we should use 
inheritance but it 'has a' shooter, movement, health, score and therefore we 
use composition. The concrete implementation of these classes is found in 
the engine folder.
